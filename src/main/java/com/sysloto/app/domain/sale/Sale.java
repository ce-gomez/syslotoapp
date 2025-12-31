package com.sysloto.app.domain.sale;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;
    private double price;
    private double factor;
    @ManyToOne(fetch = FetchType.LAZY)
    private LotteryNumber lotteryNumber;

    public static Sale of(double price, double factor, LotteryNumber lotteryNumber) {
        return new Sale(null, price, factor, lotteryNumber);
    }

    public double getPayout() {
        return price * factor;
    }
}
