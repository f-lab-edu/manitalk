package com.example.web.repository.fake;

import com.example.web.domain.UserRoomMission;
import com.example.web.repository.jpa.UserRoomMissionRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeUserRoomMissionRepository implements UserRoomMissionRepository {

    private final Map<Integer, UserRoomMission> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<UserRoomMission> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends UserRoomMission> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = entity.getUserRoom() != null ? entity.getUserRoom().getId() : idGenerator.incrementAndGet();
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
    public <S extends UserRoomMission> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(UserRoomMission entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends UserRoomMission> entities) {
        for (UserRoomMission entity : entities) {
            delete(entity);
        }
    }

    @Override
    public UserRoomMission getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    private void setId(UserRoomMission userRoomMission, Integer id) throws Exception {
        Field idField = UserRoomMission.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(userRoomMission, id);
    }
}
