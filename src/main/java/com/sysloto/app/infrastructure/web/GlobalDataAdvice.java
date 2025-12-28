package com.sysloto.app.infrastructure.web;

import com.sysloto.app.application.service.ScheduleFinder;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalDataAdvice {

    private final SellerRepository sellerRepository;
    private final ScheduleFinder scheduleFinder;

    @ModelAttribute("globalSellers")
    public Iterable<?> getGlobalSellers() {
        return sellerRepository.findAll();
    }

    @ModelAttribute("currentScheduleName")
    public String getCurrentScheduleName() {
        return scheduleFinder.findCurrentSchedule()
                .map(schedule -> schedule.getName())
                .orElse("Fuera de turno");
    }
}
