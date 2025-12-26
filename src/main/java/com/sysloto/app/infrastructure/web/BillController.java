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
    public String index(Model model) {
        // Find recent bills (simplified for now: all bills, or empty list if none)
        // Ideally we would pagination or filter by current schedule, but Requirement is
        // just "List recent bills"
        // Let's list bills from current schedule if possible, or just all.
        // Assuming findByScheduleId exists or similar. For now, fetch all or empty
        // Actually, let's just show standard list.
        model.addAttribute("bills", billRepository.findAll()); // Assuming JpaBillRepository supports findAll via
                                                               // ListCrudRepository
        model.addAttribute("sellers", sellerRepository.findAll()); // For the modal
        return "bills/index";
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
