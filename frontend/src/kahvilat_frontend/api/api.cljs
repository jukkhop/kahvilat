(ns kahvilat-frontend.api
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [chan <!]]
   [clojure.string :as str]
   [kahvilat-frontend.constants :refer [backend-endpoint]])
  (:require-macros
   [cljs.core.async.macros :refer [go]]))

(def options {:keepalive 10000 :timeout 30000 :with-credentials? false})

(defn- parse-open [open]
  (case open
    "open" :is-open
    "closed" :is-closed
    :else :unknown))

(defn fetch-info [id]
  (go
    (try
      (let [url (str backend-endpoint "/place?id=" id)
            {:keys [body, status]} (<! (http/get url options))]

        (if-not (= status 200)
          (throw (js/Error. "Fetch error")))

        (let [{:keys [open, info1]} body]
          {:open (parse-open open) :info info1}))

      (catch js/Error ex
        {:error (.-message ex)}))))
