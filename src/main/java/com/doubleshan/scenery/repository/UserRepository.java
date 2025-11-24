package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.User;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<String, User> store = new ConcurrentHashMap<>();

    public User save(User u) {
        store.put(u.getId(), u);
        return u;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByOpenId(String openId) {
        return store.values().stream().filter(x -> openId.equals(x.getOpenId())).findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
