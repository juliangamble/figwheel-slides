(ns figwheel-slides.slide00
  (:require
    [om.core :as om :include-macros true]
    [om-tools.core :refer-macros [defcomponent]]
    [sablono.core :refer-macros [html]]
    [figwheel-slides.syntax-highlight :as sx]
    ))

(def dark-green "#143")
(def light-green "#175")
(def dark-purple "#449")
(def light-purple "#6ad")
(def dark-red "#944")
(def light-red "#f8c")

(def app-state (atom {}))

(defcomponent code
  [app owner]
  (render
    [_]
    (html
      [:div.code-cb62a
       [:pre
        [:code
         (sx/cmt ";; This is a quick demo of an Om application subsequently integrated into Figwheel ") "\n"
         ;(sx/cmt ";; building a game in ClojureScript, because") "\n"
         ;(sx/cmt ";; I find the design patterns very interesting.") "\n"
         "\n"
         ;(sx/cmt ";; This requires some knowledge of ClojureScript,") "\n"
         ;(sx/cmt ";; which you can find " (sx/kw [:a {:href "https://github.com/shaunlebron/ClojureScript-Syntax-in-15-minutes"} "here"]) ".") "\n"
         ;"\n" 
         ;(sx/cmt ";; Created by " (sx/core [:a {:href "http://twitter.com/shaunlebron"} "@shaunlebron"])) "\n"
         ;(sx/cmt ";; Styling borrowed from " (sx/lit [:a {:href "http://twitter.com/ibdknox"} "@ibdknox"]) " :)") "\n"
         ;"\n\n\n\n\n\n"
         (sx/cmt ";; ") "SHIFT + RIGHT for next slide" (sx/cmt " ---------------->>") "\n"
         ;(sx/cmt ";; ") "(Swipe coming soon)"
         ]]])))

(defcomponent slide
  [app owner]
  (render
    [_]
    (html
      [:div
       [:h1 "Integrating Figwheel into your Om application"]
       (om/build code app)
       ;(om/build canvas app)
       ])))

(defn init
  [id]
  (println "init slide 00")
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