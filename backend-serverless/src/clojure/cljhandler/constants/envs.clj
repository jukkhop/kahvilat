(ns cljhandler.constants.envs
  (:require
   [clojure.string :refer [blank?]]
   [environ.core :refer [env]]))

(def cache-ttl
  (let [value (or (:cache-ttl env) "30000")]
    (Integer/parseInt value)))

(def redis-password
  (env :redis-password))

(def redis-uri
  (env :redis-uri))

(def scraper-api-key
  (env :scraper-api-key))
