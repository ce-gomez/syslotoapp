package com.sysloto.app.domain.sale;

import com.sysloto.app.domain.seller.Seller;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
    private Long scheduleId;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bill_id")
    private List<Sale> sales;

    public Bill(Long billId, LocalDateTime date, Seller seller, Long scheduleId) {
        this.billId = billId;
        this.date = date;
        this.seller = seller;
        this.scheduleId = scheduleId;
        this.sales = new ArrayList<>();
    }

    public static Bill create(Seller seller, Long scheduleId) {
        return new Bill(null, LocalDateTime.now(), seller, scheduleId);
    }

    public double getTotal() {
        return sales.stream().mapToDouble(Sale::getTotal).sum();
    }

    public void addSale(Number number, double price, double factor) {
        if (sales.stream().map(Sale::getNumber).toList().contains(number)) {
            sales.stream().filter(sale -> sale.getNumber().equals(number)).findFirst().get().setPrice(price);
        } else {
            sales.add(Sale.of(price, factor, number));
        }
    }
}
