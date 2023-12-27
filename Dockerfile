FROM quay.io/keycloak/keycloak:19.0.3

ARG listener=./keycloak-spi-kafka-listener/target/listener.jar

# copy the jars ...
COPY ${listener} /opt/keycloak/providers/