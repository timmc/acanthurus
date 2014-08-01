(defproject acanthurus "0.1.0-SNAPSHOT"
  :description "Monitor-switching script for XRandR"
  :url "https://github.com/timmc/acanthurus"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}}
  :main acanthurus.monitor
  :aot [acanthurus.monitor])
