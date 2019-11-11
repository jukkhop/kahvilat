(ns kahvilat-backend.api.place
  (:require
   [clojure.core.async :refer [chan <! go]]
   [clojure.data.json :as json]
   [org.httpkit.server :refer [with-channel send!]])
  (:use
   (kahvilat-backend.cache cache)
   (kahvilat-backend.constants cors)
   (kahvilat-backend.lib scrape)))

(defn- format-response [info]
  (let [{:keys [status, message]} info]
    (if (= status "OK")
      {:status 200 :body (merge {:status "OK"} info)}
      {:status 500 :body {:status "Error" :message message}})))

(defn place-handler [req]
  (with-channel req chan
    (go (let [id (-> req :params :id)
              info (cache-get id fetch-opening-hours)
              {:keys [status, body]} (format-response info)]
          (if-not (= (:status info) "OK")
            (cache-evict id))
          (send! chan {:status status
                       :headers (merge {"Content-Type" "text/json"} cors-headers)
                       :body (json/write-str body)}
                 true)))))
