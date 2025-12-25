package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.NumberRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNumberRepository extends NumberRepository, ListCrudRepository<LotteryNumber, Long> {
}
