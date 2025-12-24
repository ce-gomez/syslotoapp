package com.sysloto.app.domain.sale;

import java.util.Optional;

public interface NumberRepository {
    Optional<Number> findByNumberId(String numberId);
    Number save(Number number);
    void delete(Number number);
}
