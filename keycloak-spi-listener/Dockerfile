FROM quay.io/keycloak/keycloak:19.0.3
ADD --chown=keycloak:keycloak target/listener.jar /opt/keycloak/providers/listener.jar
RUN /opt/keycloak/bin/kc.sh build