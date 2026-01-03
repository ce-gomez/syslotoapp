package com.sysloto.app.domain.schedule;

import com.sysloto.app.domain.investment.Investment;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public record SaleSpecification(
        @Column(name = "start_time") LocalTime start,
        @Column(name = "end_time") LocalTime end
) {
    public boolean isSatisfiedBy(Investment investment) {
        return investment.getDate().toLocalTime().isAfter(start) && investment.getDate().toLocalTime().isBefore(end);
    }
}
