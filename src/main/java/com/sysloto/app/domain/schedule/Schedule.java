package com.sysloto.app.domain.schedule;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String name;
    @Embedded
    private SaleSpecification specification;

    public Schedule(Long scheduleId, String name) {
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public static Schedule create(String name, SaleSpecification specification) {
        var sc = new Schedule(null, name);
        sc.specification = specification;
        return sc;
    }
}
