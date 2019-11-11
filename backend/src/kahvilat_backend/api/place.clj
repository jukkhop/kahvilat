(ns kahvilat-backend.api.place
  (:require
   [clojure.core.async :refer [chan <! go]]
   [clojure.data.json :as json]
   [org.httpkit.server :refer [with-channel send!]])
  (:use
   (kahvilat-backend.cache cache)
   (kahvilat-backend.constants headers)
   (kahvilat-backend.lib scrape)))

(defn place-handler [req]
  ; Handle request asynchronously using with-channel and send!
  ;
  ; Successful requests are cached using a time-to-live cache.
  (with-channel req chan
    (go (let [id (-> req :params :id)
              info (cache-get id fetch-opening-hours)
              {:keys [status]} info]

          (if-not (= status "OK")
            (cache-evict id))

          (send! chan {:status (if (= status "OK") 200 500)
                       :headers all-headers
                       :body (json/write-str info)}
                 true)))))
