package com.sysloto.app.domain.investment;

import com.sysloto.app.domain.BaseDomainRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvestmentRepository extends BaseDomainRepository<Investment> {
    Optional<Investment> findById(UUID id);

    List<Investment> findByNumberAndScheduleAndDate(Long number, Long scheduleId, LocalDate date);

    List<Investment> findBySellerAndScheduleAndDate(Long sellerId, Long scheduleId, LocalDate date);

    List<Investment> findBySellerAndNumberAndScheduleAndDate(Long sellerId, String numberCode, Long scheduleId,
            LocalDate date);

    void deleteBySellerId(Long sellerId);
}
