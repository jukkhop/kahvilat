(ns kahvilat-frontend.store
  (:require
   [kahvilat-frontend.constants :refer [initial-places initial-secs]]
   [reagent.core :as reagent :refer [atom]]))

(defonce places-shared (atom initial-places))
(defonce secs-shared (atom initial-secs))
