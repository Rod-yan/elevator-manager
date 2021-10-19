# Elevator Manager demo application
Elevator Manager is a Java based, Spring boot REST web server application that provides resources for obtaining Elevators status information in a given Building, as well as for modifying the Elevators' current weight and current floor status within a given set of constraints.

### Building and running the application

#### Pre-requisites
* `JDK 1.8`
* `Maven`
* `Tomcat Servlet Container v9.x`

1. Checkout the `ElevatorManager` project into your local development environment
2. Build the Maven project by directly accessing the project's root folder, and executing: 
```bash
mvn clean install
```
3. Copy the resulting .war file from `<PROJECT_ROOT>/target/elevatorManager.war` into your active Tomcat webapps folder

### Resources and compatible operations
- Resource: **`<tomcat_base_url>/ElevatorManager/elevators/{denomination}`**

  - **GET**: This request will serve the client with a JSON representation of the requested Elevator, with the given *{denomination}*.
  - **PATCH**: This request will allow the client to update the status of a given Elevator. Only *currentWeight* and *currentFloor* attributes can be altered.

### Usage notes
* Some elevators have weight and floor accessibility restrictions. 
* To obtain authorization for accessing those floors, the client should add a Basic authentication header to the HTTP Request itself.
* Elevators can't go beyond or bellow the number of floors that the building currently has.
* Elevators with an exceeded weight limit will turn on their alarm, and will not operate until their current weight is reduced.