package com.listener;

import com.entity.User;
import com.repository.UserRepository;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRegistrationListener implements EventListenerProvider{
    private static final Logger LOGGER = Logger.getLogger(UserRegistrationListener.class.getName());
    private final UserRepository userRepository;
    private final KeycloakSession keycloakSession;

    public UserRegistrationListener(UserRepository userRepository, KeycloakSession keycloakSession) {
        LOGGER.log(Level.FINE, "UserRegistrationListener is initializing...");
        this.userRepository = userRepository;
        this.keycloakSession = keycloakSession;
    }

    @Override
    public void onEvent(Event event) {
        LOGGER.log(Level.INFO, "Event {0} has been triggered", event.getType());
        if (event.getType().equals(EventType.REGISTER)) {
            LOGGER.log(Level.INFO, "User with id {0} has been registered", event.getUserId());
            User user = buildUserFromEvent(event);
            userRepository.save(user);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        // Do nothing
    }

    @Override
    public void close() {
        LOGGER.log(Level.FINE, "UserRegistrationListener is closing...");
        // Do not close the repository here as this class is initialized and closed for every request
    }

    private User buildUserFromEvent(Event event) {
        User user = new User();
        user.setUsername(event.getDetails().get("username"));
        user.setEmail(event.getDetails().get("email"));
        user.setFirstName(event.getDetails().get("firstName"));
        user.setLastName(event.getDetails().get("lastName"));
        user.setTelephone(event.getDetails().get("telephone"));
        user.setIdentityProvider(event.getDetails().get("identityProvider"));
        user.setOauthFk(event.getDetails().get("oauthFk"));
        return user;
    }
}
