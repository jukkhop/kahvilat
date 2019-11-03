(ns kahvilat-backend.routes.routes
  (:require
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route])
  (:use (kahvilat-backend.api root place)))

(defroutes app-routes
  (GET "/api/v1" [] root-path)
  (GET "/api/v1/place/:id" [] place-path)
  (route/not-found "Not found"))
