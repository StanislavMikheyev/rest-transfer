# ReadMe

### Requirements
* JDK 8

### How to run
* Run application: ```./gradlew bootRun```
* Swagger UI: ```localhost:8080/swagger-ui.html```

### What is used
* Gradle
* Spring Boot
* Spring JPA
* JUnit
* Mockito
* In-memory H2 database
* Lombok
* Swagger
* Mapstruct

### What is implemented
* Solution can be tested with Swagger UI
* Currency endpoint
    * GET by id
    * GET all
* Account endpoint
    * GET by id
    * GET all
    * POST new account
    * PATCH edit existing account
    * GET transactions for given account
* Transaction endpoint
    * GET by id
    * GET all
    * POST new transaction
* Unit tests for all services

### Assumptions
* In memory H2 database is used, data.sql contains initial data
* Unknown fields are not allowed
* If some edited/referenced resource can not be found - 404
* Business rules broken - 409
* PATH method is used for updates, PUT not supported
* Currency
    * It is a separate entity
    * You can not create a new currency via API
* Account
    * You can create account with any balance
    * You can edit only ```name``` field
    * You can get all transactions for given account like ```account/{id}/transactions```
* Transaction
    * Can not be edited after creation
    * Transaction allowed only between accounts with the same currency
    * Only treasury account can have negative balance
    * Only transactions with an amount greater than 0 are permitted
    * Every transaction has a snapshot of account balances