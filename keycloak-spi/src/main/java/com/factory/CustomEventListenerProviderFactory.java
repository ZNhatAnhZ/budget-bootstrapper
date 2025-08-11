package com.factory;

import com.listener.UserRegistrationListener;
import com.repository.UserRepository;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final Logger LOGGER = Logger.getLogger(CustomEventListenerProviderFactory.class.getName());
    private final UserRepository userRepository;

    public CustomEventListenerProviderFactory() {
        LOGGER.log(Level.INFO, "CustomEventListenerProviderFactory is initializing...");
        userRepository = new UserRepository();
    }

    @Override
    public void init(Config.Scope scope) {
        // Do nothing
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Do nothing
    }

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new UserRegistrationListener(userRepository, keycloakSession);
    }

    @Override
    public void close() {
        LOGGER.log(Level.INFO, "CustomEventListenerProviderFactory is closing...");
        userRepository.close();
    }

    @Override
    public String getId() {
        return "custom-registration-event-listener";
    }
}