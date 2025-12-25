package com.sysloto.app.domain.sale;

import java.util.Optional;

public interface NumberRepository {
    Optional<LotteryNumber> findByNumberId(Long numberId);
    Optional<LotteryNumber> findByNumber(String number);
    LotteryNumber save(LotteryNumber lotteryNumber);
    void delete(LotteryNumber lotteryNumber);
}
