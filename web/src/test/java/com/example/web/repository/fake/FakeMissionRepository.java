package com.example.web.repository.fake;

import com.example.web.domain.Mission;
import com.example.web.repository.jpa.MissionRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeMissionRepository implements MissionRepository {

    private final Map<Integer, Mission> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<Mission> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends Mission> S save(S entity) {
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
    public <S extends Mission> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(Mission entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Mission> entities) {
        for (Mission entity : entities) {
            delete(entity);
        }
    }

    @Override
    public Mission getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    private void setId(Mission user, Integer id) throws Exception {
        Field idField = Mission.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }

    @Override
    public Integer count() {
        return database.size();
    }

    @Override
    public Mission getRandomMission(int range) {
        return database.get(1);
    }
}
