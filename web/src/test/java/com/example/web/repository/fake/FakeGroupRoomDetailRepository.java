package com.example.web.repository.fake;

import com.example.web.domain.GroupRoomDetail;
import com.example.web.repository.GroupRoomDetailRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeGroupRoomDetailRepository implements GroupRoomDetailRepository {

    private final Map<Integer, GroupRoomDetail> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<GroupRoomDetail> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends GroupRoomDetail> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = idGenerator.incrementAndGet();
        }
        database.put(id, entity);
        return entity;
    }

    @Override
    public <S extends GroupRoomDetail> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(GroupRoomDetail entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends GroupRoomDetail> entities) {
        for (GroupRoomDetail entity : entities) {
            delete(entity);
        }
    }

    @Override
    public GroupRoomDetail getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    @Override
    public boolean existsByIdAndRoomOwnerId(Integer roomId, Integer userId) {
        return database.values().stream()
                .anyMatch(ur -> ur.getRoom().getId().equals(roomId) && ur.getRoomOwnerId().equals(userId));
    }
}
