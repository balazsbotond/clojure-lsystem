(ns lsystem.svg
  (:require [lsystem.vectors :as v])
  (:require [clojure.string :as s])
  (:gen-class))

;;
;; SVG visualization
;;

(defn coord-comparator
  [comparator]
  (fn [coords]
    (reduce (fn [[mx my] [x y]] [(comparator mx x) (comparator my y)]) coords)))

(def coord-max (coord-comparator max))
(def coord-min (coord-comparator min))

(defn translate-to-00
  [coords]
  (let [mincoords (coord-min coords)]
    (map #(v/minus % mincoords) coords)))

(defn scale
  [width height coords]
  (let [maxcoords (coord-max coords)
        ratio (v/div [height width] maxcoords)]
    (map #(v/mul % ratio) coords)))

(defn transform
  [width height coords]
  (->> coords
       translate-to-00
       (scale width height)))

(defn coord->point
  [coord]
  (str (first coord) "," (second coord)))

(defn points
  [coords]
  (s/join " " (map coord->point coords)))

(defn line
  [points]
  (str "<polyline points=\"" points "\" />"))

(defn xml
  [width height coords]
  (str "<svg height=\"" height "\" width=\"" width "\">"
       "<g transform=\"translate(0," height ")\">"
       "<g transform=\"rotate(-90)\">"
       (-> (transform width height coords)
           points
           line)
       "</g></g>"
       "</svg>"))

(defn template
  [contents]
  (str "<style>"
       "svg { background: #000; }"
       "polyline { fill: none; stroke: #ff0; stroke-width: 1 }"
       "</style>"
       contents))

