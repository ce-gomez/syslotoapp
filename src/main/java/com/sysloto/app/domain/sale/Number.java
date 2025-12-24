package com.sysloto.app.domain.sale;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "number", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numberId;
    @Column(unique = true, nullable = false, length = 2)
    private String number;
    private double limit;

    public static Number create(String numberId, double limit) {
        return new Number(0L, numberId, limit);
    }
}
