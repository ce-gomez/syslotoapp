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
@Table(name = "lotery_number", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
public class LotteryNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numberId;
    @Column(unique = true, nullable = false, length = 2, name = "number_value")
    private String number;
    @Column(nullable = false, name = "sale_limit")
    private double limit;

    public static LotteryNumber create(String numberId, double limit) {
        return new LotteryNumber(null, numberId, limit);
    }
}
