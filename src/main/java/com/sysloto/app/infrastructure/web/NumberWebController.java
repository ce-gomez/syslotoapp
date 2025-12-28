package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.LotteryNumberStatsService;
import com.sysloto.app.application.service.RegisterLotteryNumberService;
import com.sysloto.app.application.service.UpdateLotteryNumberLimitService;
import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.infrastructure.web.dto.LotteryNumberDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/numbers")
@AllArgsConstructor
public class NumberWebController {

    private final LotteryNumberRepository lotteryNumberRepository;
    private final RegisterLotteryNumberService registerLotteryNumberService;
    private final UpdateLotteryNumberLimitService updateLotteryNumberLimitService;
    private final LotteryNumberStatsService statsService;

    @GetMapping
    public String listNumbers(@RequestParam(required = false) Long numberId, Model model) {
        List<LotteryNumber> allNumbers = lotteryNumberRepository.findAll();

        // Convertir a DTOs con estadísticas
        List<LotteryNumberDTO> numberDTOs = allNumbers.stream()
                .map(number -> {
                    double shiftSales = statsService.calculateShiftSales(number);
                    double dailySales = statsService.calculateDailySales(number);
                    return LotteryNumberDTO.fromEntity(number, shiftSales, dailySales);
                })
                .toList();

        model.addAttribute("numbers", numberDTOs);
        model.addAttribute("totalNumbers", numberDTOs.size());
        model.addAttribute("isInNumbersContext", true);

        if (numberId != null) {
            lotteryNumberRepository.findByNumberId(numberId).ifPresent(number -> {
                double shiftSales = statsService.calculateShiftSales(number);
                double dailySales = statsService.calculateDailySales(number);
                LotteryNumberDTO selectedDTO = LotteryNumberDTO.fromEntity(number, shiftSales, dailySales);
                model.addAttribute("selectedNumber", selectedDTO);
                model.addAttribute("numberId", numberId);
            });
        }

        return "numbers/list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "numbers/register";
    }

    @PostMapping("/register")
    public String registerNumber(@RequestParam String number, @RequestParam double limit) {
        try {
            registerLotteryNumberService.register(number, limit);
            return "redirect:/numbers";
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "redirect:/numbers/register?error=" + e.getMessage();
        }
    }

    @PostMapping("/edit/{id}")
    public String updateNumber(@PathVariable Long id, @RequestParam double limit) {
        try {
            updateLotteryNumberLimitService.updateLimit(id, limit);
            return "redirect:/numbers?numberId=" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/numbers?numberId=" + id + "&error=" + e.getMessage();
        }
    }
}
