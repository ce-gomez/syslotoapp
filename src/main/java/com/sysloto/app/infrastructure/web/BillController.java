package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.BillingService;
import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.domain.seller.SellerRepository; // Need this for seller list
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillingService billingService;
    private final BillRepository billRepository;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final SellerRepository sellerRepository;

    @GetMapping
    public String index(@RequestParam(required = false) Long sellerId, Model model) {
        if (sellerId != null) {
            model.addAttribute("bills", billRepository.findBySellerId(sellerId));
            model.addAttribute("selectedSellerId", sellerId);
        } else {
            model.addAttribute("bills", billRepository.findAll());
        }

        // sellers are now provided by GlobalDataAdvice as "globalSellers",
        // but we might still populate "sellers" for the internal modal if template uses
        // it explicitly.
        model.addAttribute("sellers", sellerRepository.findAll());
        return "bills/index";
    }

    @GetMapping("/s/{sellerId}/daily")
    public String getDailyBillsBySeller(@PathVariable Long sellerId, Model model) {
        var bills = billRepository.findBySellerId(sellerId);
        var seller = sellerRepository.findById(sellerId).get();
        model.addAttribute("seller", seller);
        model.addAttribute("bills", bills);
        model.addAttribute("count", bills.size());
        return "bills/billsSeller";
    }

    @PostMapping("/create")
    public String createBill(@RequestParam Long sellerId, RedirectAttributes redirectAttributes) {
        try {
            Bill bill = billingService.registerNewBill(sellerId);
            return "redirect:/bills/" + bill.getBillId();
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bills";
        }
    }

    @GetMapping("/{id}")
    public String viewBill(@PathVariable Long id, Model model) {
        Bill bill = billRepository.findByBillId(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        model.addAttribute("bill", bill);
        // Add lists for Master-Detail view
        model.addAttribute("bills", billRepository.findAll());
        model.addAttribute("sellers", sellerRepository.findAll());
        return "bills/detail";
    }

    @PostMapping("/{id}/sales")
    public String addSale(@PathVariable Long id,
            @RequestParam String number,
            @RequestParam double price,
            RedirectAttributes redirectAttributes) {
        Bill bill = billRepository.findByBillId(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        LotteryNumber lotteryNumber = lotteryNumberRepository.findByNumber(number)
                .orElseGet(() -> lotteryNumberRepository.save(LotteryNumber.create(number, 1000.0))); // Default limit

        bill.addSale(lotteryNumber, price, bill.getSeller().getFactor());
        billRepository.save(bill);

        return "redirect:/bills/" + id;
    }
}
