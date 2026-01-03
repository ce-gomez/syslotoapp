package com.sysloto.app.domain.investment;

import com.sysloto.app.domain.BaseDomainRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvestmentRepository extends BaseDomainRepository<Investment> {
    List<Investment> findByNumberAndScheduleAndDate(Long number, Long scheduleId, LocalDate date);
    List<Investment> findBySellerAndScheduleAndDate(Long sellerId, Long scheduleId, LocalDate date);
}
