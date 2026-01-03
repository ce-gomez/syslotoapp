package com.sysloto.app.domain.investment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(name = "lottery_number")
public class LotteryNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lotteryNumberId;
    @Column(unique = true)
    private String numberCode;

    public LotteryNumber(String numberCode) {
        this.numberCode = numberCode;
    }

    public static LotteryNumber of(String numberCode) {
        return new LotteryNumber(numberCode);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LotteryNumber that = (LotteryNumber) o;
        return getLotteryNumberId() != null && Objects.equals(getLotteryNumberId(), that.getLotteryNumberId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
