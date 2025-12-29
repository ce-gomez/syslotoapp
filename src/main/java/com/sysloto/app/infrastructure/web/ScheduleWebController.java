package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.RegisterScheduleService;
import com.sysloto.app.application.service.UpdateScheduleService;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Controller
@RequestMapping("/config/schedules")
@AllArgsConstructor
public class ScheduleWebController {

    private final ScheduleRepository scheduleRepository;
    private final RegisterScheduleService registerScheduleService;
    private final UpdateScheduleService updateScheduleService;

    @GetMapping
    public String listSchedules(@RequestParam(required = false) Long scheduleId, Model model) {
        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("isInConfigContext", true);

        if (scheduleId != null) {
            scheduleRepository.findByScheduleId(scheduleId).ifPresent(schedule -> {
                model.addAttribute("selectedSchedule", schedule);
                model.addAttribute("scheduleId", scheduleId);
            });
        }

        return "schedules/list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("isInConfigContext", true);
        return "schedules/register";
    }

    @PostMapping("/register")
    public String registerSchedule(@RequestParam String name,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            registerScheduleService.register(name, LocalTime.parse(startTime), LocalTime.parse(endTime));
            return "redirect:/config/schedules";
        } catch (Exception e) {
            return "redirect:/config/schedules/register?error=" + e.getMessage();
        }
    }

    @PostMapping("/edit/{id}")
    public String updateSchedule(@PathVariable Long id,
            @RequestParam String name,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            updateScheduleService.update(id, name, LocalTime.parse(startTime), LocalTime.parse(endTime));
            return "redirect:/config/schedules?scheduleId=" + id;
        } catch (Exception e) {
            return "redirect:/config/schedules?scheduleId=" + id + "&error=" + e.getMessage();
        }
    }
}
