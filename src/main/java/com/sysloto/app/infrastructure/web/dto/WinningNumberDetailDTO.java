package com.sysloto.app.infrastructure.web.dto;

public record WinningNumberDetailDTO(
        String number,
        String scheduleName,
        double totalPayout) {
}
