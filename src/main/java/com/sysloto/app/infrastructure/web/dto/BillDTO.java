package com.sysloto.app.infrastructure.web.dto;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.Sale;
import com.sysloto.app.domain.schedule.Schedule;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public record BillDTO(
        Long billId,
        String date,
        String time,
        SellerDTO seller,
        Schedule schedule,
        double total,
        List<SaleDTO> sales
) {
    public static BillDTO fromEntity(Bill bill) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, hh:mm a", new Locale("es", "ES"));
        String result = bill.getDate().format(formatter)
                .toLowerCase()
                .replace("am", "a.m.")
                .replace("pm", "p.m.")
                .replace("a. m.", "a.m.")
                .replace("p. m.", "p.m.");

        return new BillDTO(
                bill.getBillId(),
                result,
                bill.getDate().format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH))
                .toLowerCase()
                .replace("am", "a.m.")
                .replace("pm", "p.m."),
                SellerDTO.fromEntity(bill.getSeller()),
                bill.getSchedule(),
                bill.getTotal(),
                bill.getSales().stream().map(SaleDTO::fromEntity).toList()
        );
    }
}
