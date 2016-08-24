(ns paramorse.core
  (:require [clojure.string :refer [split join]]))

;; a 1 represents a moment of signal
;; a 0 represents a moment of silence.
;; a "dot" is represented by 10 (one moment of signal, one of silence)
;; a "dash" is represented by 1110 (three moments of signal, one of silence)
;; a letter is separated from the next letter by 000
;; words are separated by a space equal to seven dots 0000000

(def letter->tones
  {\A [:dot :dash]
   \B [:dash :dot :dot :dot]
   \C [:dash :dot :dash :dot]
   \D [:dash :dot :dot]
   \E [:dot]
   \F [:dot :dot :dash :dot]
   \G [:dash :dash :dot]
   \H [:dot :dot :dot :dot]
   \I [:dot :dot]
   \J [:dot :dash :dash :dash :dash]
   })

(def tones->letter (apply hash-map (mapcat reverse letter->tones)))

(def tone-encodings {:dot "1" :dash "111" "111" :dash "1" :dot})

(defn encode-letter [tones]
  (apply str (drop-last 1 (interleave (map tone-encodings tones)
                                      (repeat "0")))))

(defn decode-tones [tones]
  (tones->letter (map tone-encodings (split tones #"0"))))

(defn decode-word [word]
  (apply str (map decode-tones (split word #"000"))))

(defn decode-message [message]
  (join " " (map decode-word (split message #"0000000"))))
