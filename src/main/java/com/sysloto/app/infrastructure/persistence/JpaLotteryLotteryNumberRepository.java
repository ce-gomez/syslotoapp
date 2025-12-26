package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLotteryLotteryNumberRepository extends LotteryNumberRepository, JpaRepository<LotteryNumber, Long> {
}
