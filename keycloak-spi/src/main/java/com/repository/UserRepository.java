package com.repository;

import com.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepository() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public User save(User user) {
        sessionFactory.inTransaction(session -> {
            if (user.getId() == null || session.get(User.class, user.getId()) == null) {
                session.persist(user);
            } else {
                session.merge(user);
            }
        });
        return user;
    }

    public Optional<User> read(String id) {
        AtomicReference<Optional<User>> userResult = new AtomicReference<>();
        sessionFactory.inTransaction(session -> {
            User user = session.get(User.class, id);
            userResult.set(Optional.ofNullable(user));
        });
        return userResult.get();
    }

    public Optional<User> getUserByOauthFk(String oauthFk) {
        AtomicReference<Optional<User>> userResult = new AtomicReference<>();
        sessionFactory.inTransaction(session -> {
            User user = session.createNamedQuery("getUserByOauthFk", User.class)
                .setParameter("oauthFk", oauthFk)
                .getSingleResultOrNull();
            userResult.set(Optional.ofNullable(user));
        });
        return userResult.get();
    }

    public Optional<User> getUserByUsername(String username) {
        AtomicReference<Optional<User>> userResult = new AtomicReference<>();
        sessionFactory.inTransaction(session -> {
            User user = session.createNamedQuery("getUserByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResultOrNull();
            userResult.set(Optional.ofNullable(user));
        });
        return userResult.get();
    }

    public Optional<User> getUserByEmail(String email) {
        AtomicReference<Optional<User>> userResult = new AtomicReference<>();
        sessionFactory.inTransaction(session -> {
            User user = session.createNamedQuery("getUserByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResultOrNull();
            userResult.set(Optional.ofNullable(user));
        });
        return userResult.get();
    }

    public void delete(String id) {
        sessionFactory.inTransaction(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
        });
    }

    public List<User> getAllUsers() {
        AtomicReference<List<User>> users = new AtomicReference<>();
        sessionFactory.inTransaction(session -> users.set(session.createQuery("from user", User.class).list()));
        return users.get();
    }

    public void close() {
        sessionFactory.close();
    }
}