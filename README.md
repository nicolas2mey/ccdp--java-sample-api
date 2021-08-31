# [CCDP-OPS] Sample JAVA API application.

A sample of a SpringBoot Java web application that has full compliance with CCDP requirements.

## 1. What it does.

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


API documentation is available at: http://localhost:8080/swagger-ui.html

## 2. Technical stack.

### 2.1. Requirements.

- JDK 11+
- Maven 3.6+

### 2.2. Frameworks.

- Spring Boot 2.5+
- Resilience4j

## 3. Usage.

### 3.1. Build application.

```bash
mvn clean install
```

### 3.2. Run application.

You must define some environment variables:

```bash
export BU_CODE=frlm && \
export TANGRAM_LABEL=my-app && \
export TANGRAM_UUID=42
```

You then need a Vault token (computed on `adeo/ccdp-ops-tooling` namespace) and exports it to a `VAULT_TOKEN` environment variable:

```bash
export VAULT_TOKEN=<my_token_value>
```

Then you can finally launch application:

```bash
mvn -Dspring-boot.run.profiles=local spring-boot:run
```

## 4. Development and github workflow.

This project contains github action workflows to help in development process. Here is a description of expected usage.

### 4.1. New development.

Get last `develop` updates locally:

```bash
git pull --rebase
```

Create a new feature branch:

```bash
git checkout -b feat/my-feature-name
```

_NB: Branch name __MUST__ starts with `feat/`, words must be separated by a single `-` character._

Once you finish your developments, push your modifications to origin:

```bash
git push --set-upstream origin feat/my-feature-name
```

This push action will automatically:
- create a new branch in your github repository
- launch a maven build
- launch a Sonar analysis (and will create a Sonar branch for this feature)
- create a Docker image tagged with your feature name and push it into JFROG artifactory

Once your feature is finished, you can create a Pull Request in github.

_NB: think about grouping your commits into a single one so that develop history remains understandable._

The Pull Request process by itself will not process any build or deployment because merging Pull Request into `develop` will:
- launch a maven build
- launch a Sonar analysis
- updates and push develop Docker image into JFROG artifactory
- deploy docker image in your Turbine development environment.

### 4.2. Hot fix.

The process is pretty much the same as for features, but:

- branch must be created from `master` branch
- branch name must starts with `fix/`
- the built artifact is not deployed automatically

Once you finished your developments and merged them into `master`, you can perform a release.

__! Do not forget to merge `master` content into `develop` once release is validated !__

### 4.3. Release.

The `release` action is available through `release` job. You must:

- specify branch you wan to create a `release` tag for (should always be `master` branch)
- specify (i.e. type) the version part you want to increment:
  - `patch` (default value)
  - `minor`
  - `major`
 
## 5. CCDP Tooling integration.

- Turbine deployment: https://turbine.adeo.cloud/environments/cdp-jfl-sandbox-dev/view/DEPLOYMENTS
- Sonar: https://sonar.factory.adeo.cloud/dashboard?id=ccdp--java-sample-api
- DataDog: 
  - APM: https://app.datadoghq.eu/apm/service/ccdp-java-sample-api/netty.request?env=dev
  - logs: https://app.datadoghq.eu/logs?query=%40env%3Adev%20service%3Accdp-java-sample-api&cols=host%2Cservice%2C%40http.url_details.path%2C%40http.status_code&index=&messageDisplay=inline&stream_sort=desc
- JFrog artifactory: https://adeo.jfrog.io/ui/repos/tree/General/docker-asfr-ccdp-ops%2Fccdp-java-sample-api 