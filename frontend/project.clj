(defproject kahvilat-frontend "0.1.0-SNAPSHOT"
  :description "See which specialty cafeterias in Helsinki are currently open"
  :url "https://kahvilat.caffeinerush.dev/"

  :license {:name "MIT"}

  :min-lein-version "2.9.1"

  :dependencies [[cljs-http "0.1.46"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [org.clojure/core.async  "0.4.500"]
                 [reagent "0.8.1"]]

  :plugins [[lein-figwheel "0.5.19"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                :figwheel {:on-jsload "kahvilat-frontend.main/on-js-reload"
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:asset-path "js/compiled/out"
                           :closure-defines {kahvilat-frontend.constants/backend-api-key "my_backend_api_key"
                                             kahvilat-frontend.constants/backend-endpoint "http://localhost:8080/api/v1"
                                             kahvilat-frontend.constants/initial-delay "60"}
                           :main kahvilat-frontend.app
                           :output-dir "resources/public/js/compiled/out"
                           :output-to "resources/public/js/compiled/kahvilat_frontend.js"
                           :preloads [devtools.preload]
                           :source-map-timestamp true}}

               {:id "min"
                :source-paths ["src"]
                :compiler {:closure-defines {kahvilat-frontend.constants/backend-api-key #=(eval (or (System/getenv "BACKEND_API_KEY") ""))
                                             kahvilat-frontend.constants/backend-endpoint #=(eval (or (System/getenv "BACKEND_ENDPOINT") ""))
                                             kahvilat-frontend.constants/initial-delay #=(eval (or (System/getenv "INITIAL_DELAY") ""))}
                           :optimizations :advanced
                           :output-to "resources/public/js/compiled/kahvilat_frontend.js"
                           :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"]
             :nrepl-port 7889}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.19"]]

                   :source-paths ["src" "dev"]

                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
