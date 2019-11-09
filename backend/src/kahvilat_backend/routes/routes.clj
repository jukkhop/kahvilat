(ns kahvilat-backend.routes.routes
  (:require
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route])
  (:use (kahvilat-backend.api root place)))

(defroutes all-routes
  (GET "/api/v1" [] root-handler)
  (GET "/api/v1/place/:id" [] place-handler)
  (route/not-found "Not found"))
