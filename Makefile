build:
	./gradlew build

clean:
	./gradlew clean

runVaadinApp:
	./gradlew app-vaadin:bootRun

runJavaFxApp:
	./gradlew app-javafx:run

integrationTest:
	./gradlew integrationTest

.PHONY: build runJavaFxApp
