FROM quay.io/keycloak/keycloak:15.0.0

ARG listener=/target/listener.jar

# copy the jars ...
COPY ${listener} /opt/jboss/keycloak/standalone/deployments/

