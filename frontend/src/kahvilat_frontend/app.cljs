(ns kahvilat-frontend.app
  (:require
   [kahvilat-frontend.components.places :refer [places-component]]
   [kahvilat-frontend.store :refer [places-shared secs-shared]]
   [reagent.core :as reagent]))

(enable-console-print!)

(defn app []
  [:div.container
   [:h1.title "kahvilat.info"]
   [:h3.subtitle "Refresh in: " @secs-shared]
   [places-component @places-shared]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))
