package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.LotteryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/winning-number")
@AllArgsConstructor
public class WinningNumberWebController {
    private final LotteryService lotteryService;

    @PostMapping("/register")
    public String registerWinningNumber(@RequestParam String number, RedirectAttributes redirectAttributes) {
        try {
            lotteryService.register(number);
            redirectAttributes.addFlashAttribute("successMessage", "Número ganador registrado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
}
