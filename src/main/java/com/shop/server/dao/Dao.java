package com.shop.server.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    E save(E entity);

    void update(E entity);

    List<E> findAll();

    Optional<E> findById(K id);

    boolean delete(K id);
}
