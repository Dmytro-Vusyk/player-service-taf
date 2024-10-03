# Define the targets

# Target to run mvn clean install with skipping tests
install:
	@mvn clean install -DskipTests=true
	@echo "Maven clean install complete. Waiting for the next step..."

# Target to run the specific test class after installation
test:
	@mvn clean test -pl player-service-test -Dtest=PropertiesTest -Dmaven.test.failure.ignore=true
	@echo "Test execution completed."

# Combined target to first install and then run the test
all: install test
	@echo "Build and test process completed."

# Phony targets to avoid file conflict with real files
.PHONY: install test all
