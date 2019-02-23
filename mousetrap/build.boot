(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.3"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.6.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/mousetrap
       :version     +version+
       :description "Simple library for handling keyboard shortcuts in Javascript"
       :url         "https://craig.is/killing/mice"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"Apache 2.0" "http://www.apache.org/licenses/LICENSE-2.0"}})

(deftask package []
  (comp
    (download :url (str "https://unpkg.com/mousetrap@" +lib-version+ "/mousetrap.js"))
    (sift :move {#".*mousetrap.js" "cljsjs/mousetrap/development/mousetrap.inc.js"})
    (download :url (str "https://unpkg.com/mousetrap@" +lib-version+ "/mousetrap.min.js"))
    (sift :move {#".*mousetrap.min.js" "cljsjs/mousetrap/production/mousetrap.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.mousetrap")
    (pom)
    (jar)))
