(ns kahvilat-frontend.app
  (:require
   [kahvilat-frontend.components.places :refer [places-component]]
   [kahvilat-frontend.events :refer [emit]]
   [kahvilat-frontend.store :refer [app-state]]
   [reagent.core :refer [render]]))

(enable-console-print!)

(defn app []
  [:div.container
   [:h1.title "Helsinki specialty cafeterias"]
   [:h3.subtitle "Refresh in: " (:secs @app-state)]
   [places-component (:places @app-state)]])

(defn mount-app []
  (render [app] (.getElementById js/document "app"))
  (js/setTimeout #(emit [:update-places]) 1)
  (js/setInterval #(emit [:second-elapsed]) 1000))

(mount-app)