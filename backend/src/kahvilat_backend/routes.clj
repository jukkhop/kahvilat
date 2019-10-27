(ns kahvilat-backend.routes
  (:use (kahvilat-backend.api root place))
  (:require
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route]))

(defroutes app-routes
  (GET "/api/v1" [] root-path)
  (GET "/api/v1/place/:id" [] place-path)
  (route/not-found "Not found"))