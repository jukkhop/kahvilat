(ns kahvilat-frontend.components.places
  (:require
   [cljs.core.async :refer [chan <!]]
   [kahvilat-frontend.api :refer [fetch-info]]
   [kahvilat-frontend.components.place :refer [place-component]]
   [kahvilat-frontend.constants :refer [initial-secs]]
   [kahvilat-frontend.store :refer [places-shared secs-shared]]
   [kahvilat-frontend.utils :refer [assoc-seq]]
   [reagent.core :as reagent])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(def reset-vals {:loading true :error false :info nil})

(defn- update-place [idx item]
  (let [id (:id item)]
    (go (let [info (<! (fetch-info id))]
          (swap!
           places-shared
           (fn [xs] (assoc-seq xs idx (merge
                                       (nth xs idx)
                                       {:loading false}
                                       info))))))))

(defn- update-places []
  (swap! places-shared (fn [xs] (map #(merge % reset-vals) xs)))
  (dorun (map-indexed update-place @places-shared)))

(defn- tick []
  (when (zero? @secs-shared) (update-places))
  (swap! secs-shared #(if (pos? %) (dec %) initial-secs))
  (js/setTimeout tick 1000))

(defn places-component [items]
  (reagent/create-class
   {:component-did-mount
    #(do (update-places) (tick))

    :reagent-render
    (fn [x] [:ul (map place-component x)])}))
