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
   \K [:dash :dot :dash]
   \L [:dot :dash :dot :dot]
   \M [:dash :dash]
   \N [:dash :dot]
   \O [:dash :dash :dash]
   \P [:dot :dash :dash :dot]
   \Q [:dash :dash :dot :dash]
   \R [:dot :dash :dot]
   \S [:dot :dot :dot]
   \T [:dash]
   \U [:dot :dot :dash]
   \V [:dot :dot :dot :dash]
   \W [:dot :dash :dash]
   \X [:dash :dot :dot :dash]
   \Y [:dash :dot :dash :dash]
   \Z [:dash :dash :dot :dot]

   \1 [:dot :dash :dash :dash :dash]
   \2 [:dot :dot :dash :dash :dash]
   \3 [:dot :dot :dot :dash :dash]
   \4 [:dot :dot :dot :dot :dash :dash]
   \5 [:dot :dot :dot :dot :dot]
   \6 [:dash :dot :dot :dot :dot]
   \7 [:dash :dash :dot :dot :dot]
   \8 [:dash :dash :dash :dot :dot]
   \9 [:dash :dash :dash :dash :dot]
   \0 [:dash :dash :dash :dash :dash]
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
