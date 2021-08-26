FROM adeo-docker-public.jfrog.io/dockerfiles-collection/openjdk-jar-runner-datadog-agent:11-jre-slim-buster

COPY target/lib $USER_HOME/lib
COPY target/ccdp-java-sample-api-app.jar $USER_HOME/app.jar
