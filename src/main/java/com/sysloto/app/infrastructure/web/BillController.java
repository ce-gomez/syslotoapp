package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.BillingService;
import com.sysloto.app.application.service.ScheduleFinder;
import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.seller.SellerRepository;
import com.sysloto.app.infrastructure.web.dto.BillDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillingService billingService;
    private final BillRepository billRepository;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final SellerRepository sellerRepository;
    private final ScheduleFinder scheduleFinder;

    @GetMapping
    public String index(@RequestParam(required = false) Long sellerId, Model model) {
        if (sellerId != null) {
            model.addAttribute("bills", billRepository.findBySellerId(sellerId));
            model.addAttribute("selectedSellerId", sellerId);
        } else {
            model.addAttribute("bills", billRepository.findAll());
        }
        model.addAttribute("sellers", sellerRepository.findAll());
        return "bills/index";
    }

    @GetMapping("/s/{sellerId}/daily")
    public String getDailyBillsBySeller(
            @PathVariable Long sellerId,
            @RequestParam(required = false) Long billId,
            Model model) {
        var schedule = scheduleFinder.findCurrentSchedule()
                .map(Schedule::getScheduleId)
                .orElse(0L);
        var bills = billRepository.findBySellerScheduleDate(sellerId, schedule, LocalDate.now())
                .stream()
                .map(BillDTO::fromEntity)
                .toList();

        var seller = sellerRepository.findById(sellerId).get();
        var initials = seller.getName().charAt(0) + "" + seller.getLastname().charAt(0);

        if (billId != null) {
            billRepository.findByBillId(billId).ifPresent(bill -> {
                model.addAttribute("selectedBill", BillDTO.fromEntity(bill));
                model.addAttribute("salesCount", bill.getSales().size());
            });
        }

        model.addAttribute("initials", initials);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("seller", seller);
        model.addAttribute("bills", bills);
        model.addAttribute("count", bills.size());
        model.addAttribute("billId", billId);
        model.addAttribute("isInBillsContext", true);
        return "bills/billsSeller";
    }

    @PostMapping("/s/{sellerId}/create")
    public String newBill(@PathVariable Long sellerId, RedirectAttributes redirectAttributes) {
        try {
            Bill bill = billingService.registerNewBill(sellerId);
            return "redirect:/bills/s/" + sellerId + "/daily?billId=" + bill.getBillId();
            // return "redirect:/bills/s/" + sellerId + "/daily";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bills/s/" + sellerId + "/daily";
        }
    }

    @PostMapping("/{billId}/sales")
    public String createSale(
            @PathVariable Long billId,
            @RequestParam String number,
            @RequestParam double price,
            RedirectAttributes redirectAttributes) {
        try {
            Bill bill = billingService.createNewSale(billId, number, price);
            return "redirect:/bills/s/" + bill.getSeller().getSellerId() + "/daily?billId=" + bill.getBillId();
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
}
