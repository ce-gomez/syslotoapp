package com.sysloto.app.domain.sale;

import java.util.List;
import java.util.Optional;

public interface LotteryNumberRepository {
    Optional<LotteryNumber> findByNumberId(Long numberId);

    Optional<LotteryNumber> findByNumber(String number);

    List<LotteryNumber> findAll();

    LotteryNumber save(LotteryNumber lotteryNumber);

    void delete(LotteryNumber lotteryNumber);

    long count();
}
