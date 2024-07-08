package com.example.web.repository.jpa.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);

    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    T getReferenceById(ID id);

    boolean existsById(ID id);
}
