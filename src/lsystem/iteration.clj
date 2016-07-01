(ns lsystem.iteration
  (:require [clojure.string :as s])
  (:require [lsystem.vectors :as v])
  (:gen-class))

;;
;; L-system iteration
;;

(defn next-iter
  [rules state]
  (s/join (map #(or (rules %) %) state)))

(defn nth-iter
  [n rules initial]
  (nth (iterate (partial next-iter rules) initial) n))

(defn cleanup
  [state]
  (filter #{\F \+ \-} state))

(declare coords)

(defn forward
  [result direction position commands]
  (let [new-pos (v/forward position direction)]
    #(coords (conj result new-pos) direction new-pos commands)))

(defn right
  [result direction position commands]
  #(coords result (v/right direction) position commands))

(defn left
  [result direction position commands]
  #(coords result (v/left direction) position commands))

(def command-map
  {\F forward
   \+ right
   \- left})

(defn coords
  ([commands]
   (trampoline coords [] [1 0] [0 0] commands))
  ([result direction position [c & cs]]
   (if (nil? c)
     result
     #((command-map c) result direction position cs))))
