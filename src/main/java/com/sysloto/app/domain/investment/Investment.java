package com.sysloto.app.domain.investment;

import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.seller.Seller;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "investment")
public class Investment {
    @Id
    private UUID investmentId;
    private LocalDateTime date;
    private double amount;
    private double factor;
    @ManyToOne
    private LotteryNumber lotteryNumber;
    @ManyToOne
    private Seller seller;
    @ManyToOne
    private Schedule schedule;

    public Investment(UUID investmentId, LocalDateTime timestamp, double amount, double factor) {
        this.investmentId = investmentId;
        this.date = timestamp;
        this.amount = amount;
        this.factor = factor;
    }

    public static Investment create(
            double amount,
            double factor,
            LotteryNumber lotteryNumber
    ) {
        var id = UUID.randomUUID();
        var iv = new Investment(id, LocalDateTime.now(), amount, factor);
        iv.lotteryNumber = lotteryNumber;
        return iv;
    }

    public double getPayout() {
        return amount * factor;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Investment that = (Investment) o;
        return getInvestmentId() != null && Objects.equals(getInvestmentId(), that.getInvestmentId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
