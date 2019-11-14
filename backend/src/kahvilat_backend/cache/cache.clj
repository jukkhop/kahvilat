(ns kahvilat-backend.cache.cache
  (:require [clojure.core.cache.wrapped :as c])
  (:use (kahvilat-backend.constants envs)))

(defonce ^{:private true} cache (c/ttl-cache-factory {} :ttl cache-ttl))

(defn cache-get [key retrieve-data]
  (c/lookup-or-miss cache key retrieve-data))

(defn cache-evict [key]
  (c/evict cache key))