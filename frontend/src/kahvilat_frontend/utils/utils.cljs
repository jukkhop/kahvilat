(ns kahvilat-frontend.utils)

(defn update-elem-by-id [list id new-elem]
  (map #(if (= (:id %) id) (merge % new-elem) %) list))

(defn from-json [x]
  (js->clj (.parse js/JSON x) :keywordize-keys true))