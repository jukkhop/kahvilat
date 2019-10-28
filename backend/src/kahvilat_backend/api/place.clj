(ns kahvilat-backend.api.place
  (:require
   [clojure.data.json :as json])
  (:use (kahvilat-backend.lib scrape)))

(defn place-path [req]
  (try
    (let [id (-> req :params :id)
          info (get-opening-hours id)]
      {:status  200
       :headers {"Content-Type" "text/json"}
       :body (json/write-str (merge {:status "OK"} info))})
    (catch Exception e
      {:status 500
       :headers {"Content-Type" "text/json"}
       :body (json/write-str {:status "Error" :message (.getMessage e)})})))