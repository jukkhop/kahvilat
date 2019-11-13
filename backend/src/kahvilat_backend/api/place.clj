(ns kahvilat-backend.api.place
  (:require
   [clojure.core.async :refer [go]]
   [clojure.data.json :as json]
   [org.httpkit.server :refer [with-channel send!]])
  (:use
   (kahvilat-backend.cache redis)
   (kahvilat-backend.constants headers)
   (kahvilat-backend.lib scrape utils)))

(defn place-handler [req]
  ; Handle request asynchronously using with-channel and send!
  ;
  ; Successful requests are cached using a time-to-live cache.
  (with-channel req chan
    (go
      (let [id (-> req :query-string parse-qs :id)
            {:keys [status, body]} (if (cache-has id)
                                     (cache-get id)
                                     (fetch-opening-hours id))]

        (if (= status "OK")
          (cache-set id {:status status :body body}))

        (send! chan {:status (if (= status "OK") 200 500)
                     :headers all-headers
                     :body (json/write-str body)}
               true)))))
