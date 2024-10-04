#I am to lazy to paste this commands each time when debugging project locally.

# Define the targets

# Target to run mvn clean install with skipping tests
install:
	@mvn -B package --file pom.xml -DskipTests=true
	@echo "Maven clean install complete. Waiting for the next step..."

# Target to run the specific test class after installation
test:
	@mvn test -pl player-service-test -am -Dmaven.test.failure.ignore=true
	@echo "Test execution completed."

# Combined target to first install and then run the test
all: install test
	@echo "Build and test process completed."

# Phony targets to avoid file conflict with real files
.PHONY: install test all
