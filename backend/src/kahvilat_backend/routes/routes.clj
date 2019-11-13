(ns kahvilat-backend.routes.routes
  (:require
   [compojure.core :refer [defroutes GET]]
   [compojure.route :refer [not-found]])
  (:use
   (kahvilat-backend.api root place)))

(defroutes all-routes
  (GET "/api/v1" [] root-handler)
  (GET "/api/v1/place" [] place-handler)
  (not-found "Not found"))
