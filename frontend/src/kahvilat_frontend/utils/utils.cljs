(ns kahvilat-frontend.utils)

(defn assoc-seq [s i v]
  (map-indexed (fn [j x] (if (= i j) v x)) s))

(defn from-json [x]
  (js->clj (.parse js/JSON x) :keywordize-keys true))