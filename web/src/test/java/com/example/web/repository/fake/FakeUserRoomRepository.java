package com.example.web.repository.fake;

import com.example.web.domain.UserRoom;
import com.example.web.repository.UserRoomRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FakeUserRoomRepository implements UserRoomRepository {

    private final Map<Integer, UserRoom> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<UserRoom> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Integer> findUserIdsByRoomId(Integer roomId) {
        return database.values().stream()
                .filter(ur -> ur.getRoom().getId().equals(roomId))
                .map(ur -> ur.getUser().getId())
                .collect(Collectors.toList());
    }

    @Override
    public <S extends UserRoom> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = idGenerator.incrementAndGet();
        }
        database.put(id, entity);
        return entity;
    }

    @Override
    public <S extends UserRoom> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(UserRoom entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends UserRoom> entities) {
        for (UserRoom entity : entities) {
            delete(entity);
        }
    }

    @Override
    public UserRoom getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    @Override
    public boolean existsByUserIdAndRoomId(Integer userId, Integer roomId) {
        return database.values().stream()
                .anyMatch(ur -> ur.getUser().getId().equals(userId) && ur.getRoom().getId().equals(roomId));
    }

    @Override
    public void deleteByRoomId(Integer roomId) {
        List<Integer> idsToRemove = database.entrySet().stream()
                .filter(ur -> ur.getValue().getRoom().getId().equals(roomId))
                .map(Map.Entry::getKey)
                .toList();

        idsToRemove.forEach(database::remove);
    }
}
