package com.sysloto.app.infrastructure.api;

import com.sysloto.app.application.service.ScheduleFinder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class ScheduleRestController {

    private final ScheduleFinder scheduleFinder;

    @GetMapping("/current")
    public ResponseEntity<ScheduleDto> getCurrentSchedule() {
        return scheduleFinder.findCurrentSchedule()
                .map(s -> ResponseEntity.ok(new ScheduleDto(s.getName())))
                // Return 204 No Content or a specific "Nothing" object if out of shift
                .orElse(ResponseEntity.ok(new ScheduleDto("Fuera de turno")));
    }

    @Data
    @AllArgsConstructor
    public static class ScheduleDto {
        private String name;
    }
}
