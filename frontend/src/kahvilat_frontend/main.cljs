(ns kahvilat-frontend.main
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [chan <! >!]]
   [reagent.core :as reagent :refer [atom]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

(def PLACES (map #(assoc % :open :unknown :loading false :error false)
                 [{:id "AndanteHelsinki" :name "Andante"}
                  {:id "artisanhelsinki" :name "Artisan Café"}
                  {:id "Brooklyn-Cafe-242642239105575" :name "Brooklyn Café"}
                  {:id "Cafetoria-roastery-118271198885149" :name "Cafetoria Café & Shop"}
                  {:id "cafesucces" :name "Café Succès"}
                  {:id "goodlifecoffee" :name "Good Life Coffee"}
                  {:id "johanochnystrom.fi" :name "Johan & Nyström"}
                  {:id "kaffaroastery"  :name "Kaffa Roastery"}
                  {:id "Kahvila-Sävy-120657571290663" :name "Kahvila Sävy"}
                  {:id "pauligkulma" :name "Paulig Kulma"}]))

(def BACKEND_ENDPOINT "https://backend-node.jukkhop.now.sh")
(def UPDATE_DELAY 60)

(defonce places-shared (atom PLACES))
(defonce update-secs (atom UPDATE_DELAY))

(defn get-is-open [id chan]
  (go (let [{:keys [status, body]} (<! (http/get (str BACKEND_ENDPOINT "/" id)))]
        (if (and (= status 200) (= (:status body) "OK"))
          (>! chan (if (:is_open body) :is_open :is_closed))
          (>! chan :error)))))

(defn update-place [item]
  (let [chan (chan) id (:id item)]
    (get-is-open id chan)
    (go (let [open (<! chan)]
          (let [new-places
                (map #(if (= (:id %) id) (assoc % :open open :loading false :error (= open :error)) %) @places-shared)]
            (reset! places-shared new-places))))))

(defn do-update []
  (reset! places-shared (map #(assoc % :loading true :error false) @places-shared))
  (dorun (map update-place @places-shared)))

(defn tick []
  (when (zero? @update-secs) (do-update))
  (reset! update-secs (if (pos? @update-secs) (dec @update-secs) UPDATE_DELAY))
  (js/setTimeout tick 1000))

(defn place-component [item]
  (let [{:keys [open loading error id name]} item]
    ^{:key item}
    [:li
     [:span name " "]
     (cond
       loading [:span.badge.loading "Loading ..."]
       error [:span.badge.error "Error while fetching"]
       (= open :is_open) [:span.badge.open "Open"]
       (= open :is_closed) [:span.badge.closed "Closed"])]))

(defn places-component [items]
  (reagent/create-class
   {:component-did-mount
    (fn [] (do (do-update) (tick)))

    :reagent-render
    (fn [x] [:ul (map place-component x)])}))

(defn app []
  [:div
   [:h1 "kahvilat.info"]
   [:h3 "Refresh in: " @update-secs]
   [places-component @places-shared]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))
