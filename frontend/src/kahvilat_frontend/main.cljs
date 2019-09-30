(ns kahvilat-frontend.main
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [chan <! >!]]
   [clojure.string :as str :refer [blank?]]
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
                  {:id "pauligkulma" :name "Paulig Kulma"}
                  ]))

(def BACKEND_ENDPOINT "http://34.241.116.19:8080/")
(def UPDATE_DELAY 60)

(defonce places-shared (atom PLACES))
(defonce update-secs (atom UPDATE_DELAY))

(defn from-json [x]
  (js->clj (.parse js/JSON x) :keywordize-keys true))

(defn parse-open [open]
  (case open
    "open" :is_open
    "closed" :is_closed
    :else :unknown))

(defn get-info [id chan]
  (go (let [resp (<! (http/get (str BACKEND_ENDPOINT "/place/" id)))
            {:keys [status, open, info1]} (from-json (:body resp))]
        (if (and (= (:status resp) 200) (= status "OK"))
          (>! chan {:open (parse-open open) :info info1})
          (>! chan {:error "Error"})))))

(defn update-place [item]
  (let [chan (chan) id (:id item)]
    (get-info id chan)
    (go (let [info (<! chan)
              new-places (map #(if (= (:id %) id)
                (merge % { :loading false } info) %) @places-shared)]
          (reset! places-shared new-places)))))

(defn do-update []
  (reset! places-shared (map #(assoc % :loading true :error false :info nil) @places-shared))
  (dorun (map update-place @places-shared)))

(defn tick []
  (when (zero? @update-secs) (do-update))
  (reset! update-secs (if (pos? @update-secs) (dec @update-secs) UPDATE_DELAY))
  (js/setTimeout tick 1000))

(defn place-component [item]
  (let [{:keys [error id info loading name open]} item]
    ^{:key id}
    [:li
     [:span.name name]
     (cond
       loading [:span.badge.loading "Loading ..."]
       error [:span.badge.error "Error while fetching"]
       (= open :is_open) [:span.badge.open "Open"]
       (= open :is_closed) [:span.badge.closed "Closed"])
      (if (not (blank? info)) [:span.badge.info info] )]))

(defn places-component [items]
  (reagent/create-class
   {:component-did-mount
    (fn [] (do (do-update) (tick)))

    :reagent-render
    (fn [x] [:ul (map place-component x)])}))

(defn app []
  [:div.container
   [:h1.title "kahvilat.info"]
   [:h3.subtitle "Refresh in: " @update-secs]
   [places-component @places-shared]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))
