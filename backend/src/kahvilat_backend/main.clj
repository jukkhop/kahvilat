(ns kahvilat-backend.main
  (:gen-class)
  (:require
   [environ.core :refer [env]]
   [org.httpkit.server :as server]
   [ring.middleware.cors :refer [wrap-cors]])
  (:use (kahvilat-backend.routes routes)))

(def origin-url
  (-> :origin-url env re-pattern))

(def port
  (-> :port env Integer/parseInt))

(def app
  (-> all-routes
      (wrap-cors :access-control-allow-credentials "true"
                 :access-control-allow-headers ["Content-Type"]
                 :access-control-allow-methods [:get :options]
                 :access-control-allow-origin [origin-url])))

(defn -main []
  (server/run-server #'app {:port port})
  (println (str "Server running at port " port)))
