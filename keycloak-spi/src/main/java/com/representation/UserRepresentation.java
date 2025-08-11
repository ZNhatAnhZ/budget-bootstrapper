package com.representation;

import com.entity.User;
import com.repository.UserRepository;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class UserRepresentation extends AbstractUserAdapterFederatedStorage {
    private final UserRepository userRepository;
    private final User user;
    public UserRepresentation(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, UserRepository userRepository, User user) {
        super(session, realm, storageProviderModel);
        this.userRepository = userRepository;
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
        userRepository.save(user);
    }

    public String getPassword() {
        return user.getPassword();
    }
}
