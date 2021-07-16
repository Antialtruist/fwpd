(ns fwpd.core
  (:require [clojure.string :as s]))

(def filename "suspects.csv")
(def new-filename "new-suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\r\n")))

(defn mapify
  "Return a seq of maps ....."
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; Exercises
;; 1
(defn gfilter-list
  [minimum-glitter records]
  (map :name (filter #(>= (:glitter-index %) minimum-glitter) records)))

;; 2
(defn append [suspect records]
  (conj records suspect))

;; 3


;; 4
(defn convert-csv [maps]
  (s/join "\r\n" (map #(s/join "," [(:name %) (:glitter-index %)]) maps)))