(ns acanthurus.xrandr
  "XRandR utilities."
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn partition-at
  "Partition a collection based on a predicate that matches
partition-start items. Note that the first partition may not begin
with an item that the predicate is true for."
  [f coll]
  (->> (rest (reductions #(if (f %2) %2 %) nil coll))
       (map vector coll)
       (partition-by second)
       (map (partial map first))))

(def match-output #"^(\S+)\s+(dis)?connected\s.*")

(defn parse
  "Parse XRandR output. Output is a map of output name (string) to map
of :available (boolean) and :active (boolean)."
  [xrandr-out]
  (let [chunks (->> (str/split-lines xrandr-out)
                    (partition-at #(first (re-seq match-output %)))
                    (drop-while #(or (empty? %)
                                     (not (re-matches match-output
                                                      (first %))))))]
    (into {}
          (for [[output-line & mode-lines] chunks]
            (let [[_ output dis?] (re-find match-output output-line)
                  in-use? (boolean (some #(.contains % "*") mode-lines))]
              [output {:available (not dis?)
                       :active in-use?}])))))

(defn query-raw
  "Read raw XRandR state."
  []
  (:out (sh "xrandr" "-q")))

(defn query
  "Read and parse current XRandR state."
  []
  (parse (query-raw)))
