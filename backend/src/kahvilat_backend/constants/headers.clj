(ns kahvilat-backend.constants.headers
  (:use
   (kahvilat-backend.constants envs)))

(def content-headers
  {"Content-Type" "application/json"})

(def cors-headers
  {"Access-Control-Allow-Headers" "Content-Type, X-Api-Key"
   "Access-Control-Allow-Methods" "GET, OPTIONS"
   "Access-Control-Allow-Origin" origin-url})

(def all-headers
  (merge content-headers cors-headers))
