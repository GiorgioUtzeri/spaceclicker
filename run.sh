export SOURCES=src/main/java
export CLASSES=bin

javac -d ${CLASSES} -cp ${CLASSES} $@ `find src/main/java -name "*.java"`

export MAINCLASS=Game
java -cp ${CLASSES} $MAINCLASS