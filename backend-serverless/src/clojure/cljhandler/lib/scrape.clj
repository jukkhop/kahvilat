(ns cljhandler.lib.scrape
  (:require
   [clj-http.client :as client]
   [clojure.string :as str :refer [includes? lower-case trim]]
   [hickory.core :refer [as-hickory parse]]
   [hickory.select :as s])
  (:use
   (cljhandler.constants envs)))

(def scrape-url
  (str "http://api.scraperapi.com?api_key="
       scraper-api-key
       "&url=https://www.facebook.com/"))

(def selector
  (s/child
   (s/and
    (s/class "_4-u2")
    (s/class "_u9q")
    (s/class "_3xaf")
    (s/class "_4-u8"))
   (s/and
    (s/class "_2pi9")
    (s/class "_2pi2"))
   s/last-child
   (s/class "_4bl9")))

(defn- parse-status [info]
  (cond
    (includes? info "open now") "open"
    (includes? info "closes in") "open"
    (includes? info "closed now") "closed"
    (includes? info "opens in") "closed"
    :else "error"))

(defn- parse-info [html]
  "Parses the opening hours information from the given HTML"
  (let [tree (as-hickory (parse html))
        data (-> (s/select selector tree) last :content)
        info1 (-> data first :content first trim)
        info2 (-> data second :content first :content first trim)
        open (-> info2 lower-case parse-status)]
    {:info1 info1 :info2 info2 :open open}))

(defn fetch-opening-hours [id]
  "Attempts to fetch the opening hours by the given place id"
  (try
    (let [{:keys [body, reason-phrase, status]}
          (client/get (str scrape-url id))]
      (if (= status 200)
        {:status "OK" :body (parse-info body)}
        {:status "Error" :body {:message (str status ": " reason-phrase)}}))
    (catch Exception ex
      (println "Exception caught: " (str ex))
      {:status "Error" :body {:message (.getMessage ex)}})))
