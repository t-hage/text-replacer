https://akman.github.io/jpackage-maven-plugin/examples/from-runtime-image.html

https://docs.oracle.com/en/java/javase/16/jpackage/packaging-overview.html#GUID-C0AAEB7D-1FAB-4E20-B52C-E2401AC2BABE

Use JPackage to package the target/app

---

jpackage --input target/ \
--name 'text-replacer' \
--main-jar text-replacer-1.0-SNAPSHOT.jar \
--main-class com.tom.textreplacer.HelloApplication \
--type msi \
--java-options '--enable-preview'

jpackage --input target/ \
--name 'text-replacer' \
--main-jar text-replacer-1.0-SNAPSHOT.jar \
--main-class com.tom.textreplacer.HelloApplication \
--type pkg \
--java-options '--enable-preview'