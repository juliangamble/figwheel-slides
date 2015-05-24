(ns figwheel-slides.slide01
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
         (sx/cmt ";; Reloading code on save - without reloading the browser ") "\n"
         "\n"
         ";; This includes\n\n"
         ";;  * Minimal set of files reloaded\n\n"
         ";;  * Broadcast to all clients\n\n"
         ";;  * CSS Reloading\n\n"

         (sx/cmt ";; ") "SHIFT + RIGHT for next slide" (sx/cmt " ---------------->>") "\n"
         ]]])))

(defcomponent slide
  [app owner] 
  (render
    [_]
    (html
      [:div
       [:h1 "1. Big Ideas in Figwheel."]
       (om/build code app)
       ;(om/build canvas app)
       ])))

(defn init
  [id]
    (println "init slide 01")

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