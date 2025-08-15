FROM quay.io/keycloak/keycloak:latest

COPY target/*.jar /opt/keycloak/providers/

RUN /opt/keycloak/bin/kc.sh build