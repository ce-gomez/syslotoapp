package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.investment.LotteryNumber;
import com.sysloto.app.domain.investment.LotteryNumberRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface JpaLotteryNumberRepository extends LotteryNumberRepository, ListCrudRepository<LotteryNumber, Long> {
    @Override
    @Query("SELECT b FROM LotteryNumber b WHERE b.numberCode = :number")
    Optional<LotteryNumber> findByNumberCode(String number);
}
