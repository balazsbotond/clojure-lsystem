(ns lsystem.core
  (:require [lsystem.iteration :as iter])
  (:require [lsystem.svg :as svg])
  (:gen-class))

(def dragon-curve
  {:start "FX"
   :rules {\X "X+YF+"
           \Y "-FX-Y"}})

(defn -main
  [& args]
  (->> (iter/nth-iter 15 (:rules dragon-curve) (:start dragon-curve))
       iter/cleanup
       iter/coords
       (svg/xml 400 400)
       svg/template
       (spit "dragon.html")))
