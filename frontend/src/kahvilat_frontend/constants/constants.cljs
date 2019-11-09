(ns kahvilat-frontend.constants)

(goog-define backend-endpoint "Missing environment variable `BACKEND_ENDPOINT`")
(goog-define initial-delay "Missing environment variable `INITIAL_DELAY`")

(def initial-secs (js/parseInt initial-delay))

(def ^:private raw-places [{:id "AndanteHelsinki" :name "Andante"}
                           {:id "artisanhelsinki" :name "Artisan Café"}
                           {:id "Brooklyn-Cafe-242642239105575" :name "Brooklyn Café"}
                           {:id "Cafetoria-roastery-118271198885149" :name "Cafetoria Café & Shop"}
                           {:id "cafesucces" :name "Café Succès"}
                           {:id "goodlifecoffee" :name "Good Life Coffee"}
                           {:id "johanochnystrom.fi" :name "Johan & Nyström"}
                           {:id "kaffaroastery"  :name "Kaffa Roastery"}
                           {:id "Kahvila-Sävy-120657571290663" :name "Kahvila Sävy"}
                           {:id "pauligkulma" :name "Paulig Kulma"}])

(def ^:private initial-vals {:open :unknown :loading false :error false})

(def initial-places (map #(merge % initial-vals) raw-places))
