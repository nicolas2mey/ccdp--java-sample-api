# [CCDP-OPS] Sample JAVA API application.

A sample of a SpringBoot Java web application that has full compliance with CCDP requirements.

## What it does.

It will expose a dumb API with a few applicative routes (port 8080):

- GET `/users`
  - List all known users.
- GET `/users/{id}`
  - Gets a user through its identifier.
- POST `/users`
  - Creates a new user.
- DELETE `/users/{i}`
  - Deletes user with given identifier.
- GET `/numbers/random`
  - Gets a (useless) fact on a random number.

... and some other routes for application administration (port 8081):

- GET `/info`
  - Returns current application information like version, last commit, etc.
- GET `/health/readiness`
  - Tells whether current application is ready to get trafic or not.
- GET `/health/liveness`
  - Tells whether current application is alive and kicking or not.
- GET `/prometheus`
  - Returns prometheus metrics.

## Technical stack.

### Requirements.

- JDK 11+
- Maven 3.6+

### Stack.

- Spring Boot 2.5+

## Usage.

- Compile application:
```bash
mvn compile
```

- Run application:
```bash
mvn -Dspring-boot.run.profiles=local spring-boot:run
```
