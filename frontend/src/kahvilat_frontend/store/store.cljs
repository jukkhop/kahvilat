(ns kahvilat-frontend.store
  (:require
   [kahvilat-frontend.constants :refer [initial-places initial-secs]]
   [reagent.core :as reagent :refer [atom]]))

(defonce app-state (atom {:places initial-places
                          :secs initial-secs}))
