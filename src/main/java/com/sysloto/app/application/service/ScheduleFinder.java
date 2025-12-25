package com.sysloto.app.application.service;

import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleFinder {

    private final ScheduleRepository scheduleRepository;

    public Optional<Schedule> findCurrentSchedule() {
        var now = LocalTime.now();
        var bills = scheduleRepository.findAll();

        // This is a simplified check because SaleSpecification takes a Bill object,
        // but we just want to check time.
        // We need to access start/end directly or create a helper in
        // Schedule/Specification.
        // Since SaleSpecification is a record, we can access start() and end().

        return bills.stream()
                .filter(s -> {
                    var spec = s.getSpecification();
                    // Basic range check
                    return isTimeInRange(now, spec.start(), spec.end());
                })
                .findFirst();
    }

    private boolean isTimeInRange(LocalTime target, LocalTime start, LocalTime end) {
        // Handle normal range
        if (start.isBefore(end)) {
            return (target.equals(start) || target.isAfter(start)) && target.isBefore(end);
        } else {
            // Handle cross-day (e.g. 22:00 to 06:00) - though we initialized them separate
            return target.isAfter(start) || target.isBefore(end);
        }
    }
}
