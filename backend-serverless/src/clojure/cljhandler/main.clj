(ns cljhandler.main
  (:import (javahandler Request Response))
  (:require [clojure.string :as str :refer [blank?]])
  (:use
   (cljhandler.cache redis)
   (cljhandler.constants envs)
   (cljhandler.lib scrape)))

(gen-class
 :name "cljhandler.main"
 :methods [[handler [javahandler.Request com.amazonaws.services.lambda.runtime.Context] javahandler.Response]])

(defn -handler [this request ctx]
  (let [id (.getId request)
        {:keys [status, body]} (if (cache-has id)
                                 (cache-get id)
                                 (fetch-opening-hours id))]

    (if (= status "OK")
      (cache-set id {:status status :body body}))

    (if (= status "OK")
      (Response. status
                 (:info1 body)
                 (:info2 body)
                 (:open body))
      (Response. status
                 (:message body)))))