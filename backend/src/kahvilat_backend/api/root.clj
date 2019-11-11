(ns kahvilat-backend.api.root
  (:require
   [clojure.data.json :as json])
  (:use
   (kahvilat-backend.constants headers)))

(defn root-handler [req]
  {:status 200
   :headers all-headers
   :body (json/write-str {:status "OK"})})
