package com.sysloto.app.domain.sale;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Optional<Bill> findByBillId(Long id);
    List<Bill> findByScheduleId(Long schedule);
    //List<Bill> findBySellerId(Long sellerId);
    Bill save(Bill bill);
    List<Bill> findAll();
}
