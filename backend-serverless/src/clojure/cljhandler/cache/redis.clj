(ns cljhandler.cache.redis
  (:require [taoensso.carmine :as car :refer (wcar)])
  (:use (cljhandler.constants envs)))

(def ttl-secs (/ cache-ttl 1000))

(def conn {:pool {} :spec {:uri redis-uri
                           :password redis-password
                           :timeout 5000}})

(defmacro wcar* [& body] `(car/wcar conn ~@body))

(defn cache-has [key]
  (let [res (wcar* (car/exists key))]
    (not (= res 0))))

(defn cache-get [key]
  (wcar* (car/get key)))

(defn cache-set [key value]
  (wcar* (car/setex key ttl-secs value)))

(defn cache-del [key]
  (wcar* (car/del key)))