(ns kahvilat-frontend.store
  (:require
   [kahvilat-frontend.constants :refer [initial-places initial-secs]]
   [reagent.core :as reagent :refer [atom cursor]]))

(defonce app-state (atom {:places initial-places
                          :secs initial-secs}))

(defonce places-cursor (cursor app-state [:places]))
(defonce secs-cursor (cursor app-state [:secs]))
