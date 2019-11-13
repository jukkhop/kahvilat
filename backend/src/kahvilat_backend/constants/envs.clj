(ns kahvilat-backend.constants.envs
  (:require
   [environ.core :refer [env]]))

(def cache-ttl
  (-> :cache-ttl env Integer/parseInt))

(def origin-url
  (-> :origin-url env re-pattern))

(def port
  (-> :port env Integer/parseInt))

(def redis-password
  (env :redis-password))

(def redis-uri
  (env :redis-uri))

(def scraper-api-key
  (env :scraper-api-key))
