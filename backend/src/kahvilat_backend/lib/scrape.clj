(ns kahvilat-backend.lib.scrape
  (:require
   [clojure.core.async :refer [go]]
   [clj-http.client :as client]
   [clojure.string :as str :refer [includes? lower-case]]
   [environ.core :refer [env]]
   [hickory.core :refer [as-hickory parse]]
   [hickory.select :as s]))

(def scraper-api-key
  (env :scraper-api-key))

(def scrape-url
  (str
   "http://api.scraperapi.com?api_key="
   scraper-api-key
   "&url=https://www.facebook.com/"))

(defn- parse-status [info]
  (cond
    (includes? info "open now") :open
    (includes? info "closes in") :open
    (includes? info "closed now") :closed
    (includes? info "opens in") :closed
    :else :error))

(defn- parse-opening-hours [html]
  "Attempts to parse opening hours information from the given HTML"
  (let [tree (as-hickory (parse html))
        data (-> (s/select (s/child
                            (s/and
                             (s/class "_4-u2")
                             (s/class "_u9q")
                             (s/class "_3xaf")
                             (s/class "_4-u8"))
                            (s/and
                             (s/class "_2pi9")
                             (s/class "_2pi2"))
                            s/last-child
                            (s/class "_4bl9"))
                           tree)
                 last :content)
        info1 (-> data first :content first)
        info2 (-> data second :content first :content first)
        is_open (-> info2 lower-case parse-status)]
    {:info1 info1 :info2 info2 :open is_open}))

(defn fetch-opening-hours [id]
  "Attempts to asynchronously fetch the Facebook page with the given id"
  (go
    (try
      (let [{:keys [body, reason-phrase, status]}
            (client/get (str scrape-url id))]
        (if-not (= status 200)
          {:status "Error"
           :message (str "Facebook returned " status reason-phrase)})
        (merge {:status "OK"} (parse-opening-hours body)))
      (catch Exception ex
        {:status "Error" :message (.getMessage ex)}))))
