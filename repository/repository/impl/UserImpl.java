package repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Collection;

import model.User;
import repository.UserRepository;

public class UserImpl implements UserRepository{

    private Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
    
    @Override
    public Collection<User> findAll() {
        return users.values();
    }
}
