(ns lsystem.vectors
  (:gen-class))

;;
;; Vector operations
;;

(defn forward [v dir] (map + v dir))

(defn left [[x y]] [(- y) x])

(defn right [[x y]] [y (- x)])

(defn minus [[x y] [u v]] [(- x u) (- y v)])

(defn div [[x y] [u v]] [(double (/ x u)) (double (/ y v))])

(defn mul [[x y] [u v]] [(* x u) (* y v)])

