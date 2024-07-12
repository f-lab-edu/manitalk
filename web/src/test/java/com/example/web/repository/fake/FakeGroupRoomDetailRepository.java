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
        GroupRoomDetail entity = database.get(id);
        return (entity != null && !entity.isDeleted()) ? Optional.of(entity) : Optional.empty();
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
        GroupRoomDetail entity = database.get(id);
        if (entity != null && !entity.isDeleted()) {
            entity.setDeleted(true);
        }
    }

    @Override
    public void delete(GroupRoomDetail entity) {
        GroupRoomDetail existingEntity = database.get(entity.getId());
        if (existingEntity != null && !existingEntity.isDeleted()) {
            existingEntity.setDeleted(true);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends GroupRoomDetail> entities) {
        for (GroupRoomDetail entity : entities) {
            delete(entity);
        }
    }

    @Override
    public GroupRoomDetail getReferenceById(Integer id) {
        GroupRoomDetail entity = database.get(id);
        return (entity != null && !entity.isDeleted()) ? entity : null;
    }

    @Override
    public boolean existsById(Integer id) {
        GroupRoomDetail entity = database.get(id);
        return entity != null && !entity.isDeleted();
    }

    @Override
    public boolean existsByIdAndRoomOwnerId(Integer roomId, Integer userId) {
        return database.values().stream()
                .anyMatch(grd -> !grd.isDeleted() && grd.getRoom().getId().equals(roomId) && grd.getRoomOwnerId().equals(userId));
    }
}
