package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.NumberRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface JpaNumberRepository extends NumberRepository, ListCrudRepository<Number, String> {
}
