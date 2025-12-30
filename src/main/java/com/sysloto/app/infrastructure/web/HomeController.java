package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.FinancialStatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final FinancialStatsService financialStatsService;

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("isInHomeContext", true);
        model.addAttribute("totalDailySales", financialStatsService.calculateTotalDailySales());
        model.addAttribute("totalPayout", financialStatsService.calculateTotalPayout());
        model.addAttribute("winningDetails", financialStatsService.getWinningDetails());
        model.addAttribute("sellerStats", financialStatsService.getSellerStats());
        return "index";
    }
}
