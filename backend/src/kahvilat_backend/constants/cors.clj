(ns kahvilat-backend.constants.cors
  (:use
   (kahvilat-backend.constants envs)))

(def cors-headers
  {"Access-Control-Allow-Credentials" "true"
   "Access-Control-Allow-Headers" "Content-Type"
   "Access-Control-Allow-Methods" "GET"
   "Access-Control-Allow-Origin" origin-url})
