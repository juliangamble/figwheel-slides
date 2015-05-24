(ns ^:figwheel-always figwheel-slides.core
    (:require 

    [om.core :as om :include-macros true]
    [om-tools.core :refer-macros [defcomponent]]


              [figwheel-slides.slide00 :as slide00]
              [figwheel-slides.slide01 :as slide01]
              [figwheel-slides.slide02 :as slide02]
              ))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")



(def slides
  [{:id "slide00" :init slide00/init :resume slide00/resume :stop slide00/stop}
   {:id "slide01" :init slide01/init :resume slide01/resume :stop slide01/stop}
   {:id "slide02" :init slide02/init :resume slide02/resume :stop slide02/stop}

   ])

(def current-slide (atom nil))

;(println "test2")

(defn on-slide-change
  [_ _ i-prev i]
  (println "on slide change:" i-prev i)
  (when-not (= i-prev i)

    ; stop previous animations
    (doseq [slide slides]
      (let [elm (.getElementById js/document (:id slide))]
        (.velocity (js/$ elm) "stop")))

    ;(println "on-slide-change 2")

    ; animate to current slide
    (doseq [[j slide] (map-indexed vector slides)]
      (let [pos (-> (- j i) (* 100) (+ 50) (str "%"))
            elm (.getElementById js/document (:id slide))]
        ;(println "animate pos: " pos)
        ;(println "animate slide id: " (:id slide))
        ;(println "animate elm: " elm)
        ;(println "animate nil? i-prev: " (nil? i-prev))
        (if (nil? i-prev)
          (.css      (js/$ elm) #js {:left pos})
          (.velocity (js/$ elm) #js {:left pos} "easeOutQuad"))))

    ;(println "on-slide-change 3")

    ; call slide's state resume/stop functions
    (let [stop   (-> slides (get i-prev) :stop)
          resume (-> slides (get i) :resume)]
      (when stop (stop))
      (when resume (resume)))

    (aset js/document "location" "hash" (str i))))

(add-watch current-slide :slide on-slide-change)

(def key-names
  {37 :left
   38 :up
   39 :right
   40 :down
   32 :space})

(def key-name #(-> % .-keyCode key-names))

;(println "test3")

(def current-key-seconds (atom nil))

(defn key-down [e]
  ; need to change to be arrow key level - fires when shift key pushed as well
  
  
    
      
      (let [kname (key-name e)
            shift (.-shiftKey e)]
        (case kname
          :left  (when shift
                    ;bouncing effect after js reload?
                    ;(println "key-down" (.getSeconds (js/Date.)))
                    ;(let [key-seconds (.getSeconds (js/Date.))]
                    ;  (println "current-key-seconds: " @current-key-seconds " key-seconds: "  key-seconds)
                    ;  (when-not (= @current-key-seconds  key-seconds)
                    ;    (reset! current-key-seconds key-seconds) 
                   (swap! current-slide #(max 0 (dec %)))
                   (.preventDefault e));))
          :right (when shift
                   (swap! current-slide #(min (dec (count slides)) (inc %)))
                   (.preventDefault e))
          nil)))

(defn- on-hash-change []
  (let [hash- (.replace (aget js/document "location" "hash") #"^#" "")]
    (when (= hash- "")
      (aset js/document "location" "hash" "0"))
    (let [slide-number (js/parseInt hash-)
          slide (get slides slide-number)]
      (when slide
        (reset! current-slide slide-number)))))

;(println "test4")

(defn init-slides!
  []
  (doseq [{:keys [id init]} slides]
    (init id))) 
    ;modified for the figwheel template
    ;(init "app")))

;(println "test5")

(defn init []
  ;(println "test9")
  (init-slides!)
  ;(slide00/init "app")
  ;(println "test10")
  ;(println "test")
  ;(slide00/resume)
  (.addEventListener js/window "keydown" key-down)

  (aset js/window "onhashchange" on-hash-change)
  (on-hash-change)
  )

;TODO: swipeleft and swiperight
;https://github.com/shaunlebron/solar-system-of-js/blob/master/src/solar_system_of_js/control.cljs
(comment
  :dependencies [...
                 [cljsjs/hammer "2.0.4-4"]]
  ...
(:require
    cljsjs.hammer)
...
  (defn on-swipe!
  [type-]
  (case type-
    "tap" (next-slide!)
    "swipeleft" (next-slide!)
    "swiperight" (prev-slide!)
    nil))

(defn init-touch!
  []
  (doto (js/Hammer. canvas)
    (.on "swipeleft swiperight tap"
      #(on-swipe! (aget % "type")))))

  )

;(println "test6")

(init)

;(println "test7")

;(.addEventListener js/window "load" init)

;(println "test8")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (println "on-js-reload called")
  ;(init)
) 

