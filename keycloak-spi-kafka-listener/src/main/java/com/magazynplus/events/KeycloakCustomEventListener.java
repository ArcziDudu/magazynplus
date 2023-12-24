package com.magazynplus.events;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.services.DefaultKeycloakSession;

public class KeycloakCustomEventListener implements EventListenerProvider {
    private KeycloakSession session;

    public KeycloakCustomEventListener(KeycloakSession session) {
        this.session = session;
    }
    @Override
    public void onEvent(Event event) {
      if(event.getType().equals(EventType.REGISTER)){
          UserModel userById = session.users().getUserById(event.getUserId(), session.getContext().getRealm());
          NewUserRegisterDto newUser = new NewUserRegisterDto(userById.getFirstName(), userById.getLastName(), userById.getEmail(), userById.getUsername());
          System.out.println("2: "+newUser.getFirstName());
          System.out.println("2: "+newUser.getLastName());
          System.out.println("2: "+newUser.getUsername());
          System.out.println("2: "+newUser.getEmail());
          KafkaProducer.publishEvent("register", newUser);
      }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}