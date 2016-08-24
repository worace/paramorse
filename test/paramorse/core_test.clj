(ns paramorse.core-test
  (:require [clojure.test :refer :all]
            [paramorse.core :refer :all]))

(deftest representing-letters
  (is (= [:dot :dash] (letter->tones \A)))
  (is (= \A (tones->letter [:dot :dash])))
  (is (= [:dash :dot :dash :dot] (letter->tones \C)))
  (is (= \C (tones->letter [:dash :dot :dash :dot]))))

(deftest encoding-letter
  (is (= "1" (encode-letter [:dot])))
  (is (= "101" (encode-letter [:dot :dot])))
  (is (= "10111" (encode-letter [:dot :dash])))
  (is (= "111011101" (encode-letter [:dash :dash :dot]))))

(deftest decoding-tones
  (is (= \A (decode-tones "10111"))))

(deftest decoding-whole-word
  (is (= "AE" (decode-word "101110001"))))

(deftest decoding-multi-word-message
  (is (= "AE AE" (decode-message "1011100010000000101110001"))))

