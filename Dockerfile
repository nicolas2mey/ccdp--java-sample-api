#FROM adeo-docker-public.jfrog.io/dockerfiles-collection/temurin-jar-runner-datadog-agent:17-jre-alpine
FROM eclipse-temurin:17.0.1_12-jdk-alpine

COPY target/lib $USER_HOME/lib
COPY target/ccdp-java-sample-api.jar $USER_HOME/app.jar
