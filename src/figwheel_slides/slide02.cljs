(ns figwheel-slides.slide02
  (:require
    [om.core :as om :include-macros true]
    [om-tools.core :refer-macros [defcomponent]]
    [sablono.core :as html :refer-macros [html]]
    [figwheel-slides.syntax-highlight :as sx]
    ))

(def dark-green "#143")
(def light-green "#175")
(def dark-purple "#449")
(def light-purple "#6ad")

(def app-state (atom {:row nil :col nil}))

(defcomponent code
  [app owner]
  (render
    [_]
    (html
      [:div.code-cb62a
       [:pre
        [:code
         (sx/cmt ";; This is the third slide") "\n"
         "\n"
         ";; Here is where we see our problem with the sticky key press\n\n"
         ]]])))

(defcomponent slide
  [app owner] 
  (render
    [_]
    (html
      [:div
       [:h1 "2. The third slide."]
       (om/build code app)
       ])))

(defn init
  [id]
    (println "init slide 02")

  (om/root
    slide
    app-state
    {:target (. js/document (getElementById id))}))

(defn resume
  []
  )

(defn stop
  []
  )