(ns kahvilat-backend.api.place
  (:require
   [clojure.core.async :refer [chan <! go]]
   [clojure.data.json :as json]
   [environ.core :refer [env]]
   [org.httpkit.server :refer [with-channel send!]])
  (:use (kahvilat-backend.lib scrape)))

(def origin-url
  (-> :origin-url env re-pattern))

(defn- format-response [info]
  (let [{:keys [status, message]} info]
    (cond
      (= status "OK") {:status 200 :body (merge {:status "OK"} info)}
      :else {:status 500 :body {:status "Error" :message message}})))

(defn place-handler [req]
  (with-channel req chan
    (go (let [id (-> req :params :id)
              info (<! (fetch-opening-hours id))
              {:keys [status, body]} (format-response info)]
          (send! chan {:status status
                       :headers {"Access-Control-Allow-Credentials" "true"
                                 "Access-Control-Allow-Headers" "Content-Type"
                                 "Access-Control-Allow-Methods" "GET OPTIONS"
                                 "Access-Control-Allow-Origin" origin-url
                                 "Content-Type" "text/json"}
                       :body (json/write-str body)}
                 true)))))
