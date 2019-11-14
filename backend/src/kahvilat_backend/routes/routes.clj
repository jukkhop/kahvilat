(ns kahvilat-backend.routes.routes
  (:require
   [compojure.core :refer [defroutes GET OPTIONS]]
   [compojure.route :refer [not-found]])
  (:use
   (kahvilat-backend.api root place)))

(defroutes all-routes
  (OPTIONS "*" [] options-handler)
  (GET "/api/v1" [] root-handler)
  (GET "/api/v1/place" [] place-handler)
  (not-found "Not found"))
