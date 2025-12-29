package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.RegisterWinningNumberService;
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

    private final RegisterWinningNumberService registerWinningNumberService;

    @PostMapping("/register")
    public String registerWinningNumber(@RequestParam String number, RedirectAttributes redirectAttributes) {
        try {
            registerWinningNumberService.register(number);
            redirectAttributes.addFlashAttribute("successMessage", "Número ganador registrado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
}
