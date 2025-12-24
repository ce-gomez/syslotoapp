package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.RegisterSellerService;
import com.sysloto.app.application.service.UpdateSellerProfile;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sellers")
@AllArgsConstructor
public class SellerWebController {

    private final RegisterSellerService registerSellerService;
    private final UpdateSellerProfile updateSellerProfile;
    private final SellerRepository sellerRepository;

    @GetMapping
    public String listSellers(Model model) {
        model.addAttribute("sellers", sellerRepository.findAll());
        return "sellers/list";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "sellers/register";
    }

    @PostMapping("/register")
    public String registerSeller(@RequestParam String name,
            @RequestParam String lastname,
            @RequestParam double factor) {
        registerSellerService.register(name, lastname, factor);
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
        return "redirect:/sellers";
    }
}
