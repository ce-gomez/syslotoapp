package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.SalesService;
import com.sysloto.app.application.service.ScheduleFinder;
import com.sysloto.app.application.service.seller.RegistrationSellerService;
import com.sysloto.app.application.service.UpdateSellerProfile;
import com.sysloto.app.domain.investment.Investment;
import com.sysloto.app.domain.investment.InvestmentRepository;
import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.seller.SellerRepository;
import com.sysloto.app.infrastructure.web.dto.InvestmentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sellers")
@AllArgsConstructor
public class SellerWebController {

    private final RegistrationSellerService registrationSellerService;
    private final UpdateSellerProfile updateSellerProfile;
    private final SellerRepository sellerRepository;
    private final InvestmentRepository investmentRepository;
    private final ScheduleFinder scheduleFinder;
    private final SalesService salesService;

    @GetMapping
    public String listSellers(@RequestParam(required = false) Long sellerId, Model model) {
        model.addAttribute("sellers", sellerRepository.findAll());
        model.addAttribute("isInSellersContext", true);

        if (sellerId != null) {
            sellerRepository.findById(sellerId).ifPresent(seller -> {
                model.addAttribute("selectedSeller", seller);
                model.addAttribute("sellerId", sellerId);
            });
        }

        return "sellers/list";
    }

    @GetMapping("/{id}/sales")
    public String listSales(@PathVariable Long id, Model model) {
        var schedule = scheduleFinder.findCurrentSchedule()
                .map(Schedule::getScheduleId)
                .orElse(0L);
        var investments = investmentRepository
                .findBySellerAndScheduleAndDate(id, schedule, LocalDate.now());

        var seller = sellerRepository.findById(id).get();

        var limitsMap = seller.getLimits().stream()
                .collect(Collectors.toMap(
                        sl -> sl.getLimited().getNumberCode(),
                        sl -> sl.getAmount().doubleValue()
                ));

        var dto = investments.stream()
                .collect(Collectors.groupingBy(
                        i -> i.getLotteryNumber().getNumberCode(),
                        Collectors.summingDouble(Investment::getAmount)
                ))
                .entrySet().stream()
                .map(entry -> {
                    String numberCode = entry.getKey();
                    Double totalAmount = entry.getValue();

                    // Si no hay límite definido para ese número, podemos asumir 0 o un valor base.
                    Double limitConfigured = limitsMap.getOrDefault(numberCode, 0.0);
                    Double remainingLimit = limitConfigured - totalAmount;

                    return new InvestmentDTO(
                            numberCode,
                            totalAmount.toString(),
                            remainingLimit.toString()
                    );
                }).toList();

        var total = investments.stream().mapToDouble(Investment::getAmount).sum();

        var initials = seller.getName().charAt(0) + "" + seller.getLastname().charAt(0);
        model.addAttribute("isInSalesContext", true);
        model.addAttribute("investments", dto);
        model.addAttribute("seller", seller);
        model.addAttribute("initials", initials);
        model.addAttribute("total", total);
        model.addAttribute("sellerId", id);
        return "sellers/sales";
    }

    @PostMapping("/{id}/sales")
    public String createSale(@PathVariable Long id, @RequestParam String number, @RequestParam double amount, RedirectAttributes redirectAttributes) {
        try {
            salesService.createNewSale(id, number, amount);
            return "redirect:/sellers/{id}/sales";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/sellers/{id}/sales";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("isInSellersContext", true);
        return "sellers/register";
    }

    @PostMapping("/register")
    public String registerSeller(
            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam double factor) {
        registrationSellerService.register(name, lastname, factor);
        return "redirect:/sellers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seller Id:" + id));
        model.addAttribute("seller", seller);
        return "sellers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateSeller(@PathVariable Long id,
            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam double factor) {
        updateSellerProfile.updateProfile(id, name, lastname, factor);
        return "redirect:/sellers?sellerId=" + id;
    }
}
