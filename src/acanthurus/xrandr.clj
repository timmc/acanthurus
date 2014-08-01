(ns acanthurus.xrandr
  "XRandR utilities."
  (:require [clojure.java.shell :refer [sh]]))

(defn query
  "Read current XRandR state."
  []
  (let [output (:out (sh "xrandr" "-q"))]
    output))
