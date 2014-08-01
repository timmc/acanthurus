(ns acanthurus.monitor
  "Monitor and update displays."
  (:require [acanthurus.xrandr :as xrandr]))

(defn run
  [& args]
  (println (xrandr/query)))

(defn -main
  "Run and then shut down agents. (If you don't want this, call #'run.)"
  [& args]
  (run)
  (shutdown-agents))
