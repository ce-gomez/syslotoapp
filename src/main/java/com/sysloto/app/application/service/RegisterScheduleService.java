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
public class RegisterScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule register(String name, LocalTime startTime, LocalTime endTime) {
        // Validar que no exista un turno con el mismo nombre
        if (scheduleRepository.findByName(name).isPresent()) {
            throw new IllegalStateException("Ya existe un turno con el nombre: " + name);
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }

        Schedule schedule = Schedule.create(name, new SaleSpecification(startTime, endTime));
        return scheduleRepository.save(schedule);
    }
}
