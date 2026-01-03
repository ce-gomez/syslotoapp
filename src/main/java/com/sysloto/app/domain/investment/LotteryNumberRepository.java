package com.sysloto.app.domain.investment;

import com.sysloto.app.domain.BaseDomainRepository;

import java.util.Optional;

public interface LotteryNumberRepository extends BaseDomainRepository<LotteryNumber> {
    Optional<LotteryNumber> findByLotteryNumberId(Long numberId);
    Optional<LotteryNumber> findByNumberCode(String number);
    long count();
}
