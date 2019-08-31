(defproject kahvilat-backend "0.1.0"

  :dependencies [; Clojure compiler
                 [org.clojure/clojure "1.10.0"]
                  ; ClojureScript compiler
                 [org.clojure/clojurescript "1.10.520"]
                  ; Core async
                 [org.clojure/core.async  "0.4.500"]
                  ; Use Promises in a nice way
                 [kitchen-async "0.1.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :compiler {:main kahvilat-backend.main
                           :output-dir "target-dev"
                           :output-to "target-dev/main.js"
                           :optimizations :none
                           :pretty-print true
                           :target :nodejs
                           :install-deps true
                           :infer-externs true
                           :npm-deps {:puppeteer "1.19.0" :express "4.17.1" :cors "2.8.5"}
                           :closure-defines {'kahvilat-backend.main/origin-url "http://localhost:3449"
                                             'kahvilat-backend.main/port "8080"
                                             'kahvilat-backend.main/scrape-url "https://www.facebook.com/"}}}
               {:id "prod"
                :source-paths ["src"]
                :compiler {:main kahvilat-backend.main
                           :output-dir "target-prod"
                           :output-to "target-prod/main.js"
                           :optimizations :none
                           :pretty-print false
                           :target :nodejs
                           :install-deps true
                           :infer-externs true
                           :npm-deps {:puppeteer "1.19.0" :express "4.17.1" :cors "2.8.5"}
                           :closure-defines {'kahvilat-backend.main/origin-url ""
                                             'kahvilat-backend.main/port ""
                                             'kahvilat-backend.main/scrape-url "https://www.facebook.com/"}}}]})