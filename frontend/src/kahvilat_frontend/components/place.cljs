(ns kahvilat-frontend.components.place
  (:require
   [clojure.string :as str :refer [blank?]]))

(defn place-component [item]
  (let [{:keys [error id info loading name open]} item]
    ^{:key id}
    [:li
     [:span.name name]
     (cond
       loading [:span.badge.loading "Loading ..."]
       error [:span.badge.error "Error fetching"]
       (= open :is_open) [:span.badge.open "Open"]
       (= open :is_closed) [:span.badge.closed "Closed"])
     (if (not (blank? info)) [:span.badge.info info])]))
