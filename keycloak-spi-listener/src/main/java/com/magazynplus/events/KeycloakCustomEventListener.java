package com.magazynplus.events;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

public class KeycloakCustomEventListener implements EventListenerProvider {
    private final KeycloakSession session;

    public KeycloakCustomEventListener(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType().equals(EventType.LOGIN)) {
            UserModel userById = session.users().getUserById(event.getUserId(), session.getContext().getRealm());
            KafkaProducer.publishEventLogin("login", userById.getUsername());
        } else if (event.getType().equals(EventType.REGISTER)) {
            UserModel userById = session.users().getUserById(event.getUserId(), session.getContext().getRealm());
            NewUserRegisterDto newUser = new NewUserRegisterDto(userById.getFirstName(), userById.getLastName(), userById.getEmail(), userById.getUsername());
            KafkaProducer.publishEventRegister("register", newUser);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}