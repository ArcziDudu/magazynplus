FROM quay.io/keycloak/keycloak:18.0.0

ARG AUTHENTICATOR_JAR=target/api-gateway-1.jar

# copy the jars ...
COPY ${AUTHENTICATOR_JAR} /opt/jboss/keycloak/standalone/deployments/

