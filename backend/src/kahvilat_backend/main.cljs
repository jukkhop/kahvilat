(ns kahvilat-backend.main
  (:require
   [clojure.string :as str]
   [express]
   [kitchen-async.promise :as p]
   cors
   puppeteer))

(enable-console-print!)

(goog-define origin-url "http://localhost:3449")
(goog-define port "8080")
(goog-define scrape-url "https://www.facebook.com/")

(def pup (js/require "puppeteer"))

(defonce server (atom nil))

(defn to-json [x]
  (.stringify js/JSON (clj->js x)))

(defn is-open [id]
  (p/let [browser (.launch pup)
          page (.newPage browser)]
    (p/try
      (.goto page (str scrape-url id))
      (p/let [body (.content page)]
        (cond
          (str/includes? body "Avoinna nyt") :open
          (str/includes? body "Nyt suljettu") :closed
          :else :error))
      (p/catch js/Error e
        (print e)
        :error)
      (p/finally
        (.close browser)))))

(defn root-path [req res]
  (.send res (to-json {:status "Ok"})))

(defn is-open-path [req res]
  (let [id (.-id (.-params req))]
    (p/let [is_open (is-open id)]
      (case is_open
        :open (.send res (to-json {:status "OK" :is_open true}))
        :closed (.send res (to-json {:status "OK" :is_open false}))
        :error (do
                 (.status res 500)
                 (.send res (to-json {:status "Error" :message "Something went wrong"})))))))

(defn start-server []
  (let [app (express)]
    (.use app (cors #js{:origin origin-url :credentials true}))
    (.get app "/" root-path)
    (.get app "/is-open/:id" is-open-path)
    (.listen app (js/parseInt port) #(println "Server started at port" port))))

(defn start! []
  (reset! server (start-server)))

(defn restart! []
  (.close @server start!))

(set! *main-cli-fn* start!)
