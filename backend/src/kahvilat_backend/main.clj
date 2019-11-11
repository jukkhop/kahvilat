(ns kahvilat-backend.main
  (:gen-class)
  (:require
   [org.httpkit.server :as server])
  (:use
   (kahvilat-backend.routes routes)
   (kahvilat-backend.constants envs headers)))

(defn -main []
  (server/run-server all-routes {:port port})
  (println (str "Server running at port " port)))
