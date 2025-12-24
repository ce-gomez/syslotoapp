package com.sysloto.app.domain.schedule;

import java.time.LocalTime;
import java.util.Optional;

public interface ScheduleRepository {
    Optional<Schedule> findByScheduleId(Long scheduleId);
    Optional<Schedule> findByName(String name);
    Optional<Schedule> findByTime(LocalTime time);
    Schedule save(Schedule schedule);
    void delete(Schedule schedule);
}
