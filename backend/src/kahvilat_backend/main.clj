(ns kahvilat-backend.main
  (:use (kahvilat-backend routes))
  (:require
   [environ.core :refer [env]]
   [org.httpkit.server :as server]
   [ring.middleware.cors :refer [wrap-cors]]))

(def origin-url
  (-> :origin-url env re-pattern))

(def port
  (env :port))

(def app
  (-> app-routes
      (wrap-cors :access-control-allow-credentials "true"
                 :access-control-allow-headers ["Content-Type"]
                 :access-control-allow-methods [:get :options]
                 :access-control-allow-origin [origin-url])))

(defn -main []
  (let [port (Integer/parseInt port)]
    (server/run-server #'app {:port port})
    (println (str "Server running at port " port))))
