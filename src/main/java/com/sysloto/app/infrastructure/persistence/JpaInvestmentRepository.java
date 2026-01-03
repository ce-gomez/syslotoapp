package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.investment.Investment;
import com.sysloto.app.domain.investment.InvestmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaInvestmentRepository extends InvestmentRepository, ListCrudRepository<Investment, UUID> {
    @Override
    @Query("SELECT b FROM Investment b WHERE b.lotteryNumber.lotteryNumberId = :number AND b.schedule.scheduleId = :scheduleId AND CAST(b.date AS localdate) = :date")
    List<Investment> findByNumberAndScheduleAndDate(Long number, Long scheduleId, LocalDate date);

    @Override
    @Query("SELECT b FROM Investment b inner join Seller s on s.sellerId = b.seller.sellerId WHERE s.sellerId = :sellerId AND b.schedule.scheduleId = :scheduleId AND CAST(b.date AS localdate) = :date")
    List<Investment> findBySellerAndScheduleAndDate(Long sellerId, Long scheduleId, LocalDate date);

    @Override
    @Query("SELECT b FROM Investment b WHERE b.seller.sellerId = :sellerId AND b.lotteryNumber.numberCode = :numberCode AND b.schedule.scheduleId = :scheduleId AND CAST(b.date AS localdate) = :date")
    List<Investment> findBySellerAndNumberAndScheduleAndDate(Long sellerId, String numberCode, Long scheduleId,
            LocalDate date);
}
