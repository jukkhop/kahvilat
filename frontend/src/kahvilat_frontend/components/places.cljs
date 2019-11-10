(ns kahvilat-frontend.components.places
  (:require
   [kahvilat-frontend.components.place :refer [place-component]]))

(defn places-component [places]
  [:ul (map place-component places)])
