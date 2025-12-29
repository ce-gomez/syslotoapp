package com.sysloto.app.domain.sale;

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

    @Column(name = "winning_number", nullable = false)
    private String number;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public WinningNumber(String number, LocalDate date, Schedule schedule) {
        this.number = number;
        this.date = date;
        this.schedule = schedule;
    }
}
