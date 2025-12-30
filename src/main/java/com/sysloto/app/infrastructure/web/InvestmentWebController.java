package com.sysloto.app.infrastructure.web;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/investments")
@RequiredArgsConstructor
public class InvestmentWebController {

    private final BillRepository billRepository;
    private final ScheduleRepository scheduleRepository;

    @GetMapping
    public String index(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Long scheduleId,
            @RequestParam(required = false) Long billId,
            Model model) {

        LocalDate selectedDate = date != null ? date : LocalDate.now();
        List<Bill> bills;

        if (scheduleId != null && scheduleId > 0) {
            bills = billRepository.findByDateAndSchedule(selectedDate, scheduleId);
        } else {
            bills = billRepository.findByDate(selectedDate);
        }

        if (billId != null) {
            billRepository.findByBillId(billId).ifPresent(bill -> {
                model.addAttribute("selectedBill", com.sysloto.app.infrastructure.web.dto.BillDTO.fromEntity(bill));
                model.addAttribute("salesCount", bill.getSales().size());
            });
        }

        model.addAttribute("bills", bills);
        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("selectedScheduleId", scheduleId);
        model.addAttribute("selectedBillId", billId);
        model.addAttribute("isInInvestmentsContext", true);

        return "investments/index";
    }
}
