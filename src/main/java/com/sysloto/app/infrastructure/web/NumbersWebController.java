package com.sysloto.app.infrastructure.web;

import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Controller
@RequestMapping("/numbers")
@AllArgsConstructor
public class NumbersWebController {

    private final SellerRepository sellerRepository;

    @GetMapping
    public String listNumbers(@RequestParam(required = false) Long sellerId, Model model) {
        var sellers = sellerRepository.findAll();
        model.addAttribute("sellers", sellers);
        model.addAttribute("isInNumbersContext", true);
        model.addAttribute("sellerId", sellerId);

        if (sellerId != null) {
            sellerRepository.findById(sellerId).ifPresent(seller -> {
                model.addAttribute("selectedSeller", seller);
            });
        }

        return "numbers/list";
    }

    @PostMapping("/limits/{limitId}")
    @Transactional
    public String updateLimit(
            @PathVariable Long limitId,
            @RequestParam Long sellerId,
            @RequestParam double amount) {

        var seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seller Id:" + sellerId));

        seller.getLimits().stream()
                .filter(l -> l.getLimitId().equals(limitId))
                .findFirst()
                .ifPresent(l -> l.setAmount(BigDecimal.valueOf(amount)));

        sellerRepository.save(seller);

        return "redirect:/numbers?sellerId=" + sellerId;
    }
}
