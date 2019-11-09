(ns kahvilat-frontend.api
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [chan <! >!]]
   [clojure.string :as str]
   [kahvilat-frontend.constants :refer [backend-endpoint]]
   [kahvilat-frontend.utils :refer [from-json]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(defn- parse-open [open]
  (case open
    "open" :is_open
    "closed" :is_closed
    :else :unknown))

(defn fetch-info [id]
  (go
    (try
      (let [url (str backend-endpoint "/place/" id)
            res (<! (http/get url {:keepalive 10000}))]

        (if-not (= (:status res) 200)
          (throw (js/Error. "Fetch error")))

        (let [{:keys [status, open, info1]} (from-json (:body res))]
          (if-not (= status "OK")
            (throw (js/Error. "Parse error")))
          {:open (parse-open open) :info info1}))

      (catch js/Error ex
        {:error (.-message ex)}))))
