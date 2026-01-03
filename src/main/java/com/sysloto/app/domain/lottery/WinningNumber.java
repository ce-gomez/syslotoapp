package com.sysloto.app.domain.lottery;

import com.sysloto.app.domain.investment.LotteryNumber;
import com.sysloto.app.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "winning_number")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WinningNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lottery_number_id", nullable = false)
    private LotteryNumber number;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public WinningNumber(Long id, LotteryNumber number, LocalDate date, Schedule schedule) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.schedule = schedule;
    }

    public static WinningNumber create(LotteryNumber number, LocalDate date, Schedule schedule) {
        return new WinningNumber(null, number, date, schedule);
    }
}
