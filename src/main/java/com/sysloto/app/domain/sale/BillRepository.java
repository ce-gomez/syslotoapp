package com.sysloto.app.domain.sale;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Optional<Bill> findByBillId(Long id);

    // List<Bill> findByScheduleId(Long schedule);
    List<Bill> findBySellerId(Long sellerId);

    List<Bill> findBySellerSchedule(Long sellerId, Long scheduleId);

    List<Bill> findBySellerScheduleDate(Long sellerId, Long scheduleId, LocalDate date);

    Bill save(Bill bill);

    List<Bill> findAll();

    void deleteById(Long id);
}
