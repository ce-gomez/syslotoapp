package com.sysloto.app.domain.sale;

import com.sysloto.app.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface WinningNumberRepository extends JpaRepository<WinningNumber, Long> {
    Optional<WinningNumber> findByDateAndSchedule(LocalDate date, Schedule schedule);

    List<WinningNumber> findAllByDate(LocalDate date);
}
