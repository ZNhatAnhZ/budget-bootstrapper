package com.factory;

import com.repository.UserRepository;
import com.storage.CustomUserStorageProvider;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {
    private static final Logger LOGGER = Logger.getLogger(CustomUserStorageProviderFactory.class.getName());
    private final UserRepository userRepository;

    public CustomUserStorageProviderFactory() {
        LOGGER.log(Level.INFO, "CustomUserStorageProviderFactory is initializing...");
        userRepository = new UserRepository();
    }

    @Override
    public CustomUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return new CustomUserStorageProvider(userRepository, keycloakSession, componentModel);
    }

    @Override
    public void close() {
        LOGGER.log(Level.INFO, "CustomUserStorageProviderFactory is closing...");
        userRepository.close();
        UserStorageProviderFactory.super.close();
    }

    @Override
    public String getId() {
        return "custom-user-storage-provider-factory";
    }
}
