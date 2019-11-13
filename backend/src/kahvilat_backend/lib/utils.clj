(ns kahvilat-backend.lib.utils
  (:require
   [clojure.walk :refer [keywordize-keys]]
   [ring.util.codec :refer [form-decode]]))

(defn parse-qs [qs]
  "Parse parameters from a query string"
  (keywordize-keys (form-decode qs)))
