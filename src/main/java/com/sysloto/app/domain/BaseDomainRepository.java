package com.sysloto.app.domain;

import java.util.List;

public interface BaseDomainRepository<T> {
    T save(T entity);
    void delete(T entity);
    List<T> findAll();
}
