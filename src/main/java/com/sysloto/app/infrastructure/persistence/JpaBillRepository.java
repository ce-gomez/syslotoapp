package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.Bill;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBillRepository extends ListCrudRepository<Bill, Long> {
}