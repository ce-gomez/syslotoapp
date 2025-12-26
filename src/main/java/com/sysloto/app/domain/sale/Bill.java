package com.sysloto.app.domain.sale;

import com.sysloto.app.domain.schedule.Schedule;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bill_id")
    private List<Sale> sales;

    public Bill(Long billId, LocalDateTime date, Seller seller, Schedule schedule) {
        this.billId = billId;
        this.date = date;
        this.seller = seller;
        this.schedule = schedule;
        this.sales = new ArrayList<>();
    }

    public static Bill create(Seller seller, Schedule schedule) {
        return new Bill(null, LocalDateTime.now(), seller, schedule);
    }

    public double getTotal() {
        return sales.stream().mapToDouble(Sale::getTotal).sum();
    }

    public void addSale(LotteryNumber lotteryNumber, double price, double factor) {
        if (sales.stream().map(Sale::getLotteryNumber).toList().contains(lotteryNumber)) {
            sales.stream().filter(sale -> sale.getLotteryNumber().equals(lotteryNumber)).findFirst().get().setPrice(price);
        } else {
            sales.add(Sale.of(price, factor, lotteryNumber));
        }
    }
}
