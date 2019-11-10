(ns kahvilat-frontend.events
  (:require
   [cljs.core.async :refer [<!]]
   [kahvilat-frontend.api :refer [fetch-info]]
   [kahvilat-frontend.constants :refer [initial-secs reset-values]]
   [kahvilat-frontend.store :refer [app-state]]
   [kahvilat-frontend.utils :refer [assoc-seq]]
   [reagent.core :refer [rswap!]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(declare emit)

(defn- update-place [idx place]
  (go (let [info (<! (fetch-info (:id place)))
            new-place (merge place {:loading false} info)]
        (emit [:update-place new-place idx]))))

(defn- event-handler [state [event-name value idx]]
  (let [{:keys [places, secs]} state]
    (case event-name
      :second-elapsed (do
                        (when (zero? secs) (emit [:update-places]))
                        (assoc state :secs
                               (if (pos? secs) (dec secs) initial-secs)))

      :update-places (let [new-places (map #(merge % reset-values) places)]
                       (dorun (map-indexed update-place new-places))
                       (assoc state :places new-places))

      :update-place (assoc state :places
                           (assoc-seq places idx value))

      state)))

(defn emit [e]
  (rswap! app-state event-handler e))
