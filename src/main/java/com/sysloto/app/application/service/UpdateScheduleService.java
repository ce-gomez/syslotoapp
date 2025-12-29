package com.sysloto.app.application.service;

import com.sysloto.app.domain.schedule.SaleSpecification;
import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class UpdateScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void update(Long id, String name, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = scheduleRepository.findByScheduleId(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el turno con ID: " + id));

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }

        schedule.setName(name);
        schedule.setSpecification(new SaleSpecification(startTime, endTime));
        scheduleRepository.save(schedule);
    }
}
