package com.example.web.repository.fake;

import com.example.web.domain.ManitoRoomDetail;
import com.example.web.repository.jpa.ManitoRoomDetailRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeManitoRoomDetailRepository implements ManitoRoomDetailRepository {

    private final Map<Integer, ManitoRoomDetail> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public Optional<ManitoRoomDetail> findById(Integer id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public <S extends ManitoRoomDetail> S save(S entity) {
        Integer id = entity.getId();
        if (entity.getId() == null) {
            id = idGenerator.incrementAndGet();
        }
        database.put(id, entity);
        return entity;
    }

    @Override
    public <S extends ManitoRoomDetail> List<S> saveAll(Iterable<S> entities) {
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
    public void delete(ManitoRoomDetail entity) {
        database.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends ManitoRoomDetail> entities) {
        for (ManitoRoomDetail entity : entities) {
            delete(entity);
        }
    }

    @Override
    public ManitoRoomDetail getReferenceById(Integer id) {
        return database.get(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return database.values().stream()
                .anyMatch(ur -> ur.getId().equals(id));
    }

    @Override
    public boolean existsByGroupRoomId(Integer groupRoomId) {
        return database.values().stream()
                .anyMatch(mrd -> mrd.getGroupRoomDetail().getId().equals(groupRoomId));
    }
}
