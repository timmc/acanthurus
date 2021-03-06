(ns acanthurus.monitor
  "Monitor and update displays."
  (:require [acanthurus.xrandr :as xrandr]
            [clojure.java.shell :refer [sh]])
  (:gen-class))

(defn check-external-monitor
  [xstate]
  (let [laptop (get xstate "LVDS1")
        vga (get xstate "VGA1")]
    (when (and (:available vga)
               (not (:active vga)))
      (println "Discovered VGA available but not active.")
      (println "Activating VGA1")
      (sh "xrandr" "--output" "VGA1" "--auto")
      (when (:active laptop)
        (println "Deactivating laptop")
        (sh "xrandr" "--output" "LVDS1" "--off")))
    (when (and (not (:active laptop))
               (not (:available vga)))
      (println "Discovered VGA unavailable but laptop display inactive.")
      (println "Activating laptop")
      (sh "xrandr" "--output" "VGA1" "--off" "--output" "LVDS1" "--auto"))))

(defn check
  "Run the monitoring check itself."
  []
  (let [xstate (xrandr/query)]
    (check-external-monitor xstate)))

(defn stop?
  "Yield true when monitoring should be ended."
  []
  ;; TODO
  false)

(def pause-file ".acanthurus-pause")

(defn skip?
  "Yield true when monitoring should be skipped for this round."
  []
  (.exists (java.io.File. (System/getProperty "user.home") pause-file)))

(def interval-ms
  "Monitoring interval in milliseconds."
  3000)

(defn run
  "Run the monitoring loop until stop? returns true."
  [& args]
  (while (not (stop?))
    (when-not (skip?)
      (check))
    (Thread/sleep interval-ms)))

(defn -main
  "Run and then shut down agents. (If you don't want this, call #'run.)"
  [& args]
  (run)
  (shutdown-agents))
