FROM quay.io/keycloak/keycloak:19.0.3
RUN apt-get update && apt-get install -y nginx

RUN rm /etc/nginx/sites-enabled/default
COPY nginx.conf /etc/nginx/sites-available/default
COPY target/listener.jar /opt/jboss/keycloak/providers/
EXPOSE 80 8080
CMD service nginx start && /opt/jboss/tools/docker-entrypoint.sh -b 0.0.0.0
