package com.example.web.repository.fake;

import com.example.web.domain.User;
import com.example.web.repository.jpa.UserRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeUserRepository implements UserRepository {

    private final Map<Integer, User> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends User> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = idGenerator.incrementAndGet();
            try {
                setId(entity, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        database.put(id, entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            save(entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        database.remove(id);
    }

    @Override
    public void delete(User entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        for (User entity : entities) {
            delete(entity);
        }
    }

    @Override
    public User getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return database.values().stream()
                .filter(ur -> ur.getEmail().equals(email))
                .findFirst();
    }

    private void setId(User user, Integer id) throws Exception {
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }
}
