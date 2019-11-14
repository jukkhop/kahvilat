(defproject clj-lambda-test "0.1.0"
  :aot :all

  :dependencies [[clj-http "3.10.0"]
                 [com.amazonaws/aws-lambda-java-core "1.2.0"]
                 [com.taoensso/carmine "2.19.1"]
                 [environ "1.1.0"]
                 [hickory "0.7.1"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async  "0.4.500"]
                 [org.clojure/data.json "0.2.6"]]

  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :main cljhandler.main
  :plugins [[lein-environ "1.1.0"]]
  :source-paths ["src/clojure"])
