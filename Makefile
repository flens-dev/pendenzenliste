build:
	./gradlew build

clean:
	./gradlew clean

runVaadinApp:
	./gradlew app-vaadin:bootRun

runJavaFxApp:
	./gradlew app-javafx:run

.PHONY: build runJavaFxApp
