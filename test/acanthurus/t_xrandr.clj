(ns acanthurus.t-xrandr
  (:require [acanthurus.xrandr :as x]
            [midje.sweet :refer :all]))

(facts "partition-at"
  (tabular
   (x/partition-at even? ?in) => ?out
   ?in ?out
   [] []
   [1] [[1]]
   [2] [[2]]
   [1 2 4] [[1] [2] [4]]
   [1 3 5 6 8 10 11 13 14 15] [[1 3 5] [6] [8] [10 11 13] [14 15]]))

(def sample-output
  "Screen 0: minimum 320 x 200, current 1600 x 900, maximum 8192 x 8192
LVDS1 connected 1600x900+0+0 (normal left inverted right x axis y axis) 309mm x 174mm
   1600x900       60.0*+   40.0  
   1440x900       59.9  
   1360x768       59.8     60.0  
   1152x864       60.0  
   1024x768       60.0  
   800x600        60.3     56.2  
   640x480        59.9  
VGA1 disconnected (normal left inverted right x axis y axis)
HDMI1 disconnected (normal left inverted right x axis y axis)
DP1 disconnected (normal left inverted right x axis y axis)
HDMI2 disconnected (normal left inverted right x axis y axis)
HDMI3 disconnected (normal left inverted right x axis y axis)
DP2 disconnected (normal left inverted right x axis y axis)
DP3 disconnected (normal left inverted right x axis y axis)")

(facts "parse"
  (x/parse sample-output) => {"LVDS1" {:available true, :active true},
                              "VGA1" {:available false, :active false},
                              "HDMI1" {:available false, :active false},
                              "DP1" {:available false, :active false},
                              "HDMI2" {:available false, :active false},
                              "HDMI3" {:available false, :active false},
                              "DP2" {:available false, :active false},
                              "DP3" {:available false, :active false}})
