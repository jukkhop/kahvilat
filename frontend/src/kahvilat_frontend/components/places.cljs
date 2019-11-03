(ns kahvilat-frontend.components.places
  (:require
   [cljs.core.async :refer [chan <!]]
   [kahvilat-frontend.api :refer [fetch-info]]
   [kahvilat-frontend.components.place :refer [place-component]]
   [kahvilat-frontend.constants :refer [initial-seconds]]
   [kahvilat-frontend.store :refer [places-shared secs-shared reset-places reset-secs]]
   [kahvilat-frontend.utils :refer [update-elem-by-id]]
   [reagent.core :as reagent])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(defn update-place [item]
  (let [chan (chan)
        id (:id item)]
    (fetch-info id chan)
    (go (let [info (<! chan)
              new-elem (merge {:loading false} info)
              new-places (update-elem-by-id @places-shared id new-elem)]
          (reset-places new-places)))))

(defn update-places []
  (let [new-places (map #(assoc % :loading true :error false :info nil) @places-shared)]
    (reset-places new-places)
    (dorun (map update-place @places-shared))))

(defn tick []
  (when (zero? @secs-shared) (update-places))
  (reset-secs (if (pos? @secs-shared) (dec @secs-shared) initial-seconds))
  (js/setTimeout tick 1000))

(defn places-component [items]
  (reagent/create-class
   {:component-did-mount
    (fn [] (do (update-places) (tick)))

    :reagent-render
    (fn [x] [:ul (map place-component x)])}))

