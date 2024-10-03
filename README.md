# Test Automation Framework for intermediate and large projects.

This is test-task for technical interview.

### Stack: Java 11, TestNG, RestAssured

### Framework purpose. 
Utilization of multimodule capabilities for easy and fast code sharing between repositories and different teams.

### Packages purpose
- models - package for sharing of dto and other POJO`s
- modules - package for different API implementations like REST, SOAP API, DB connectors, etc.
- player-service-core - core framework features like adaptors, listeners, utils, etc
- player-service-test - module to store tests for player-service-api. Ideally should be rebased into separate repository
### Schema

```
├── README.md
├── models
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── companyname
│           │           └── models
│           │               └── playerserviceapi
│           └── resources
├── modules
│   ├── player-service-api
│   │   ├── pom.xml
│   │   └── src
│   │       └── main
│   │           ├── java
│   │           │   └── com
│   │           │       └── companyname
│   │           │           └── endpoints
│   │           └── resources
│   └── pom.xml
├── player-service-core
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── companyname
│           │           ├── config
│           │           ├── enums
│           │           ├── factories
│           │           ├── testutils
│           │           │   └── listeners
│           │           └── utils
│           └── resources
│               └── config
│                   └── env
├── player-service-test
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── companyname
│       │   │           ├── assertions
│       │   │           └── testutils
│       │   └── resources
│       └── test
│           ├── java
│           │   └── com
│           │       └── companyname
│           │           └── playerservice
│           │               ├── functionaltests
│           │               └── securitytests
│           └── resources
│               ├── playerservice
│               │   └── schemas
│               └── suites
└── pom.xml
```
### Installation guide
- clone repo:
```
git@github.com:Dmytro-Vusyk/player-service-taf.git
```  
- navigate to project directory
- open terminal window
- execute maven command from project root folder:
```
mvn test -pl player-service-test -am
```

