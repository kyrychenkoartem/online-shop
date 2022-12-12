package com.shop.server.dao;

import com.shop.server.mapper.extractor.EntityExtractor;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    E save(E entity);

    void update(E entity);

    List<E> findAll(EntityExtractor<E> extractor);

    Optional<E> findById(K id, EntityExtractor<E> extractor);

    boolean delete(K id);
}
