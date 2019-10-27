(ns kahvilat-backend.api.root
  (:require
   [clojure.data.json :as json]))

(defn root-path [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:status "OK"})})