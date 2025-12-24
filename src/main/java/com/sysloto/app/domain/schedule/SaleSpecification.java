package com.sysloto.app.domain.schedule;

import com.sysloto.app.domain.sale.Bill;
import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public record SaleSpecification(
        LocalTime start, LocalTime end
) {
    public boolean isSatisfiedBy(Bill bill) {
        return bill.getDate().toLocalTime().isAfter(start) && bill.getDate().toLocalTime().isBefore(end);
    }
}
