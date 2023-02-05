build:
	./gradlew build

clean:
	./gradlew clean

runJavaFxApp:
	./gradlew app-javafx:run

.PHONY: build runJavaFxApp
