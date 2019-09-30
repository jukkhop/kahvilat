(ns kahvilat-backend.core
  (:gen-class)
  (:require
   [clj-http.client :as client]
   [clojure.data.json :as json]
   [clojure.string :as str :refer [includes? lower-case]]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [hickory.core :refer [as-hickory parse]]
   [hickory.select :as s]
   [org.httpkit.server :as server]
   [ring.middleware.cors :refer [wrap-cors]]))

(def origin-url #"https://kahvilat.netlify.com")
(def port "8080")
(def scrape-url "https://www.facebook.com/")

(defn parse-open [info]
  (cond
    (includes? info "open now") :open
    (includes? info "closes in") :open
    (includes? info "closed now") :closed
    (includes? info "opens in") :closed
    :else :error))

(defn opening-info [id]
  (let [{:keys [status, body]} (client/get (str scrape-url id))
        tree (as-hickory (parse body))
        data (-> (s/select (s/child
                            (s/and
                             (s/class "_4-u2")
                             (s/class "_u9q")
                             (s/class "_3xaf")
                             (s/class "_4-u8"))
                            (s/and
                             (s/class "_2pi9")
                             (s/class "_2pi2"))
                            s/last-child
                            (s/class "_4bl9"))
                           tree)
                 last :content)
        info1 (-> data first :content first)
        info2 (-> data second :content first :content first)
        is_open (parse-open (lower-case info2))]
    {:info1 info1 :info2 info2 :open is_open}))

(defn root-path [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:status "OK"})})

(defn place-path [req]
  (let [id (-> req :params :id)
        info (opening-info id)]
    {:status  200
     :headers {"Content-Type" "text/json"}
     :body (json/write-str (merge {:status "OK"} info))}))

(defroutes app-routes
  (GET "/" [] root-path)
  (GET "/place/:id" [] place-path)
  (route/not-found "Not found"))

(def app
  (-> app-routes
      (wrap-cors
       :access-control-allow-credentials "true"
       :access-control-allow-headers ["Content-Type"]
       :access-control-allow-methods [:get :options]
       :access-control-allow-origin [origin-url])))

(defn -main
  [& args]
  (let [port (Integer/parseInt port)]
    (server/run-server #'app {:port port})
    (println (str "Server running at port " port))))
