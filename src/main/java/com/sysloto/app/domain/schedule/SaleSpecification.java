package com.sysloto.app.domain.schedule;

import com.sysloto.app.domain.sale.Bill;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public record SaleSpecification(
        @Column(name = "start_time") LocalTime start,
        @Column(name = "end_time") LocalTime end
) {
    public boolean isSatisfiedBy(Bill bill) {
        return bill.getDate().toLocalTime().isAfter(start) && bill.getDate().toLocalTime().isBefore(end);
    }
}
