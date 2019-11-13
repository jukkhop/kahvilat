(defproject kahvilat-backend "0.1.0"

  :dependencies [[caribou/clojure.walk2 "0.1.0"]
                 [clj-http "3.10.0"]
                 [compojure "1.6.1"]
                 [environ "1.1.0"]
                 [hickory "0.7.1"]
                 [http-kit "2.3.0"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async  "0.4.500"]
                 [org.clojure/core.cache "0.8.2"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-codec "1.1.2"]]

  :main kahvilat-backend.main

  :plugins [[lein-environ "1.1.0"]]

  :repl-options {:init-ns kahvilat-backend.main})
