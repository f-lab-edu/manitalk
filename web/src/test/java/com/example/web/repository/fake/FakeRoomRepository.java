package com.example.web.repository.fake;

import com.example.web.domain.Room;
import com.example.web.enums.RoomType;
import com.example.web.repository.RoomRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeRoomRepository implements RoomRepository {

    private final Map<Integer, Room> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<Room> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends Room> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = idGenerator.incrementAndGet();
        }
        database.put(id, entity);
        return entity;
    }

    @Override
    public <S extends Room> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(Room entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Room> entities) {
        for (Room entity : entities) {
            delete(entity);
        }
    }

    @Override
    public Room getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    @Override
    public boolean existsByIdAndType(Integer id, RoomType type) {
        return database.values().stream()
                .anyMatch(r -> r.getId().equals(id) && r.getType().equals(type));
    }
}
