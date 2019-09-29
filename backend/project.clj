(defproject kahvilat-backend "0.1.0"

  :dependencies [[clj-http "3.10.0"]
                 [compojure "1.6.1"]
                 [hickory "0.7.1"]
                 [http-kit "2.3.0"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async  "0.4.500"]
                 [org.clojure/data.json "0.2.6"]
                 [ring-cors "0.1.13"]]

  :repl-options {:init-ns kahvilat-backend.core}

  :main kahvilat-backend.core)
