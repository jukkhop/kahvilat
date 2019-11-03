(ns kahvilat-frontend.constants)

(def initial-delay 60)
(def backend-endpoint "https://kahvilat-backend-v2.caffeinerush.dev/api/v1")

(def raw-places [{:id "AndanteHelsinki" :name "Andante"}
                 {:id "artisanhelsinki" :name "Artisan Café"}
                 {:id "Brooklyn-Cafe-242642239105575" :name "Brooklyn Café"}
                 {:id "Cafetoria-roastery-118271198885149" :name "Cafetoria Café & Shop"}
                 {:id "cafesucces" :name "Café Succès"}
                 {:id "goodlifecoffee" :name "Good Life Coffee"}
                 {:id "johanochnystrom.fi" :name "Johan & Nyström"}
                 {:id "kaffaroastery"  :name "Kaffa Roastery"}
                 {:id "Kahvila-Sävy-120657571290663" :name "Kahvila Sävy"}
                 {:id "pauligkulma" :name "Paulig Kulma"}])

(def initial-places (map #(assoc % :open :unknown :loading false :error false) raw-places))
