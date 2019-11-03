(ns kahvilat-backend.lib.scrape
  (:require
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

(defn parse-status [info]
  (cond
    (includes? info "open now") :open
    (includes? info "closes in") :open
    (includes? info "closed now") :closed
    (includes? info "opens in") :closed
    :else :error))

(defn get-opening-hours [id]
  "Fetches the Facebook page with the given id and attempts to parse
  information about the opening hours"
  (let [{:keys [status, body]} (client/get (str scrape-url id))
        tree (as-hickory (parse body))
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
        is_open (parse-status (lower-case info2))]
    {:info1 info1 :info2 info2 :open is_open}))