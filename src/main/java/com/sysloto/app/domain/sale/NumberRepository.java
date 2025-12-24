package com.sysloto.app.domain.sale;

import java.util.Optional;

public interface NumberRepository {
    Optional<Number> findByNumberId(Long numberId);
    Optional<Number> findByNumber(String number);
    Number save(Number number);
    void delete(Number number);
}
