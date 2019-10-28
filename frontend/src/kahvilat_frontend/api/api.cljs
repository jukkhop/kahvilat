(ns kahvilat-frontend.api
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [chan <! >!]]
   [clojure.string :as str]
   [kahvilat-frontend.constants :refer [backend-endpoint]]
   [kahvilat-frontend.utils :refer [from-json]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(defn parse-open [open]
  (case open
    "open" :is_open
    "closed" :is_closed
    :else :unknown))

(defn fetch-info [id chan]
  (go
    (try
      (let [resp (<! (http/get (str backend-endpoint "/place/" id)))
            {:keys [status, open, info1]} (from-json (:body resp))]
        (if (and (= (:status resp) 200) (= status "OK"))
          (>! chan {:open (parse-open open) :info info1})
          (>! chan {:error "Error"})))
      (catch js/Error ex
        (>! chan {:error (.-message ex)})))))

