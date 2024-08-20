package com.example.web.repository.fake;

import com.example.web.domain.UserRoom;
import com.example.web.repository.jpa.UserRoomRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FakeUserRoomRepository implements UserRoomRepository {

    private final Map<Integer, UserRoom> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<UserRoom> findById(Integer id) {
        UserRoom entity = database.get(id);
        return (entity != null && !entity.isDeleted()) ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public List<Integer> findUserIdsByRoomId(Integer roomId) {
        return database.values().stream()
                .filter(ur -> !ur.isDeleted() && ur.getRoom().getId().equals(roomId))
                .map(ur -> ur.getUser().getId())
                .collect(Collectors.toList());
    }

    @Override
    public <S extends UserRoom> S save(S entity) {
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
        UserRoom entity = database.get(id);
        if (entity != null && !entity.isDeleted()) {
            entity.setDeleted(true);
        }
    }

    @Override
    public void delete(UserRoom entity) {
        UserRoom existingEntity = database.get(entity.getId());
        if (existingEntity != null && !existingEntity.isDeleted()) {
            existingEntity.setDeleted(true);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends UserRoom> entities) {
        for (UserRoom entity : entities) {
            delete(entity);
        }
    }

    @Override
    public UserRoom getReferenceById(Integer id) {
        UserRoom entity = database.get(id);
        return (entity != null && !entity.isDeleted()) ? entity : null;
    }

    @Override
    public boolean existsById(Integer id) {
        UserRoom entity = database.get(id);
        return entity != null && !entity.isDeleted();
    }

    @Override
    public boolean existsByUserIdAndRoomId(Integer userId, Integer roomId) {
        return database.values().stream()
                .anyMatch(ur -> !ur.isDeleted() && ur.getUser().getId().equals(userId) && ur.getRoom().getId().equals(roomId));
    }

    @Override
    public List<UserRoom> findByRoomId(Integer roomId) {
        return database.values().stream()
                .filter(ur -> !ur.isDeleted() && ur.getRoom().getId().equals(roomId))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findIdByUserIdAndRoomId(Integer userId, Integer roomId) {
        return Objects.requireNonNull(database.values().stream()
                .filter(ur -> ur.getUser().getId().equals(userId) && ur.getRoom().getId().equals(roomId))
                .findFirst().orElse(null)).getId();
    }

    @Override
    public List<UserRoom> findAllByRoomIdIn(List<Integer> roomIds) {
        return database.values().stream()
                .filter(ur -> !ur.isDeleted() && roomIds.contains(ur.getRoom().getId()))
                .collect(Collectors.toList());
    }

    private void setId(UserRoom userRoom, Integer id) throws Exception {
        Field idField = UserRoom.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(userRoom, id);
    }
}
