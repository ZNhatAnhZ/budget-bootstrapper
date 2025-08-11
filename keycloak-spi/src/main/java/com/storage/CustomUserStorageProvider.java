package com.storage;

import com.entity.User;
import com.repository.UserRepository;
import com.representation.UserRepresentation;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomUserStorageProvider implements UserLookupProvider, CredentialInputValidator, UserStorageProvider {
    private final UserRepository userRepository;
    private final KeycloakSession session;
    private final ComponentModel model;
    private static final Logger LOGGER = Logger.getLogger(CustomUserStorageProvider.class.getName());
    public CustomUserStorageProvider(UserRepository userRepository, KeycloakSession session, ComponentModel model) {
        LOGGER.log(Level.FINE, "CustomUserStorageProvider is initializing...");
        this.userRepository = userRepository;
        this.session = session;
        this.model = model;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        LOGGER.log(Level.INFO, "supportsCredentialType({0})", credentialType);
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        LOGGER.log(Level.INFO, "isConfiguredFor({0}, {1}, {2})", new Object[]{realm, user, credentialType});
        return supportsCredentialType(credentialType) && getPassword(user) != null;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        LOGGER.log(Level.INFO, "isValid({0}, {1}, {2})", new Object[]{realm, user, credentialInput});
        if (!(credentialInput instanceof UserCredentialModel)) return false;
        if (supportsCredentialType(credentialInput.getType())) {
            String password = getPassword(user);
            return password != null && password.equals(credentialInput.getChallengeResponse());
        } else {
            return false; // invalid cred type
        }
    }

    @Override
    public void close() {
        LOGGER.log(Level.FINE, "CustomUserStorageProvider is closing...");
        // Do not close the repository here as this class is initialized and closed for every request
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        LOGGER.log(Level.INFO, "getUserById({0}, {1})", new Object[]{realm, id});
        Optional<User> user = userRepository.getUserByOauthFk(id);
        return user.map(value -> new UserRepresentation(session, realm, model, userRepository, value)).orElse(null);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        LOGGER.log(Level.INFO, "getUserByUsername({0}, {1})", new Object[]{realm, username});
        Optional<User> user = userRepository.getUserByUsername(username);
        return user.map(value -> new UserRepresentation(session, realm, model, userRepository, value)).orElse(null);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        LOGGER.log(Level.INFO, "getUserByEmail({0}, {1})", new Object[]{realm, email});
        Optional<User> user = userRepository.getUserByEmail(email);
        return user.map(value -> new UserRepresentation(session, realm, model, userRepository, value)).orElse(null);
    }

    public String getPassword(UserModel user) {
        String password = null;
        if (user instanceof UserRepresentation userRepresentation) {
            password = userRepresentation.getPassword();
        }
        return password;
    }
}
