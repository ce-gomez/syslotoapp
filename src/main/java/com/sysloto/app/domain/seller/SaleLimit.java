package com.sysloto.app.domain.seller;

import com.sysloto.app.domain.investment.LotteryNumber;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sale_limit")
public class SaleLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long limitId;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "lottery_number_id")
    private LotteryNumber limited;

    public SaleLimit(BigDecimal amount, LotteryNumber limited) {
        this.amount = amount;
        this.limited = limited;
    }

    public static SaleLimit of(BigDecimal amount, LotteryNumber limited) {
        return new SaleLimit(amount, limited);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SaleLimit saleLimit = (SaleLimit) o;
        return getLimitId() != null && Objects.equals(getLimitId(), saleLimit.getLimitId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
