(ns kahvilat-backend.api.place
  (:require
   [clojure.core.async :refer [go]]
   [clojure.data.json :refer [write-str]]
   [clojure.string :refer [blank?]]
   [org.httpkit.server :refer [with-channel send!]])
  (:use
   (kahvilat-backend.cache cache)
   (kahvilat-backend.constants headers)
   (kahvilat-backend.lib scrape utils)))

(defn- get-id [req]
  (if-not (blank? (:query-string req))
    (-> req :query-string parse-qs :id)))

(defn place-handler [req]
  "Handle request asynchronously using with-channel and send!
   Successful requests are cached using a time-to-live cache."
  (with-channel req chan
    (go
      (if-let [id (get-id req)]
        ; Given id parameter
        (let [{:keys [status, body]} (cache-get id fetch-opening-hours)]

          (if-not (= status "OK")
            (cache-evict id))

          (send! chan {:status (if (= status "OK") 200 500)
                       :headers all-headers
                       :body (write-str body)}
                 true))

        ; Missing id parameter
        (send! chan {:status 400
                     :headers all-headers
                     :body (write-str {:message "Missing id parameter"})}
               true)))))
