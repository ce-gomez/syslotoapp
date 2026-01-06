package com.sysloto.app.infrastructure.web;

import com.sysloto.app.domain.investment.Investment;
import com.sysloto.app.domain.investment.InvestmentRepository;
import com.sysloto.app.domain.lottery.WinningNumber;
import com.sysloto.app.domain.lottery.WinningNumberRepository;
import com.sysloto.app.infrastructure.web.dto.DrawSummaryDTO;
import com.sysloto.app.infrastructure.web.dto.SellerDrawSummaryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/draws")
@AllArgsConstructor
public class DrawWebController {

    private final WinningNumberRepository winningNumberRepository;
    private final InvestmentRepository investmentRepository;

    @GetMapping
    public String listDraws(@RequestParam(defaultValue = "today") String filter, Model model) {
        List<WinningNumber> winners;
        if ("today".equals(filter)) {
            winners = winningNumberRepository.findAllByDate(LocalDate.now());
        } else {
            winners = winningNumberRepository.findAll();
        }

        List<DrawSummaryDTO> summaries = winners.stream().map(winner -> {
            var investments = investmentRepository.findByNumberAndScheduleAndDate(
                    winner.getNumber().getLotteryNumberId(),
                    winner.getSchedule().getScheduleId(),
                    winner.getDate());

            double totalCollected = investments.stream().mapToDouble(Investment::getAmount).sum();
            double totalPayout = investments.stream().mapToDouble(Investment::getPayout).sum();

            var sellerSummaries = investments.stream()
                    .collect(Collectors.groupingBy(i -> i.getSeller().getName() + " " + i.getSeller().getLastname()))
                    .entrySet().stream()
                    .map(entry -> new SellerDrawSummaryDTO(
                            entry.getKey(),
                            entry.getValue().stream().mapToDouble(Investment::getAmount).sum(),
                            entry.getValue().stream().mapToDouble(Investment::getPayout).sum()))
                    .collect(Collectors.toList());

            return new DrawSummaryDTO(
                    winner.getNumber().getNumberCode(),
                    winner.getDate(),
                    winner.getSchedule().getName(),
                    totalCollected,
                    totalPayout,
                    sellerSummaries);
        }).collect(Collectors.toList());

        model.addAttribute("draws", summaries);
        model.addAttribute("filter", filter);
        model.addAttribute("isInDrawsContext", true);
        return "draws/list";
    }
}
