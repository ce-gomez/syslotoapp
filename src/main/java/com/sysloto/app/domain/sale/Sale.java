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
    private Number number;

    public static Sale of(double price, double factor, Number number) {
        return new Sale(0L, price, factor, number);
    }

    public double getTotal() {
        return price * factor;
    }
}
