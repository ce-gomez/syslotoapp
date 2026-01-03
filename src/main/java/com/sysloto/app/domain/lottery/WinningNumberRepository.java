package com.sysloto.app.domain.lottery;

import com.sysloto.app.domain.BaseDomainRepository;
import com.sysloto.app.domain.schedule.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WinningNumberRepository extends BaseDomainRepository<WinningNumber> {
    Optional<WinningNumber> findByDateAndSchedule(LocalDate date, Schedule schedule);
    List<WinningNumber> findAllByDate(LocalDate date);
}
