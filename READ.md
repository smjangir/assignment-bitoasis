
# Getting Started

API documentation can be found at {{port}}//swagger-ui-custom.html

## Build and Test

### Dependencies

1. The project requires MySQL 

### Local setup

1. Clone the repository.

2. Update the *src/main/resources/application.properties* file as per you MySql


### Build 

If you are using IntelliJ IDEA ultimate the project should be automatically picked up as a Spring project, 
you can directly run it form the IDE. IntelliJ community and Eclipse based IDEs can import and directly run it as a maven project.

Through the command line:

1. The project uses maven, so head over to the root and run `mvn clean -f pom.xml`.

2. Run the tests with `mvn test -f pom.xml`

3. Package with `mvn package -f pom.xml`.