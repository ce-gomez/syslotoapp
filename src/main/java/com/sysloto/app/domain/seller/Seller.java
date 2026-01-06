package com.sysloto.app.domain.seller;

import com.sysloto.app.domain.investment.LotteryNumber;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;
    private String name;
    private String lastname;
    private BigDecimal factor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    @ToString.Exclude
    private List<SaleLimit> limits;

    public Seller(Long sellerId, String name, String lastname, BigDecimal factor) {
        this.sellerId = sellerId;
        this.name = name;
        this.lastname = lastname;
        this.factor = factor;
        this.limits = new ArrayList<>();
    }

    public static Seller create(String name, String lastname, BigDecimal factor) {
        return new Seller(null, name, lastname, factor);
    }

    public void setOrUpdateLimit(LotteryNumber n, BigDecimal amount) {
        var existingLimit = limits.stream()
                .filter(sl -> sl.getLimited().equals(n))
                .findFirst();

        if (existingLimit.isPresent()) {
            existingLimit.get().setAmount(amount);
        } else {
            limits.add(SaleLimit.of(amount, n));
        }
    }

    public List<SaleLimit> getLimits() {
        return List.copyOf(limits);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass)
            return false;
        Seller seller = (Seller) o;
        return getSellerId() != null && Objects.equals(getSellerId(), seller.getSellerId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
