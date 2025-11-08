package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForexCloseController {

    private final ForexTradeService forexTradeService;

    @Autowired
    public ForexCloseController(ForexTradeService forexTradeService) {
        this.forexTradeService = forexTradeService;
    }

    @PostMapping("/forex-close")
    @ResponseBody
    public CloseResponse closeTrade(@RequestBody CloseRequest request) {
        try {
            var result = forexTradeService.closeTrade(request.getTradeId());
            if (result != null) {
                return new CloseResponse(
                        "Sikeres zárás piaci áron!",
                        result.getTradeId(),
                        result.getInstrument(),
                        result.getClosedPrice(),
                        "OK"
                );
            } else {
                return new CloseResponse(
                        "Nem található ilyen trade ID.",
                        request.getTradeId(),
                        null,
                        0.0,
                        "HIBA"
                );
            }
        } catch (Exception e) {
            return new CloseResponse(
                    "Hiba történt: " + e.getMessage(),
                    request.getTradeId(),
                    null,
                    0.0,
                    "HIBA"
            );
        }
    }

    // --- DTO osztályok ---
    public static class CloseRequest {
        private long tradeId;
        public long getTradeId() { return tradeId; }
        public void setTradeId(long tradeId) { this.tradeId = tradeId; }
    }

    public static class CloseResponse {
        private String message;
        private long tradeId;
        private String instrument;
        private double closedPrice;
        private String status;

        public CloseResponse(String message, long tradeId, String instrument, double closedPrice, String status) {
            this.message = message;
            this.tradeId = tradeId;
            this.instrument = instrument;
            this.closedPrice = closedPrice;
            this.status = status;
        }

        public String getMessage() { return message; }
        public long getTradeId() { return tradeId; }
        public String getInstrument() { return instrument; }
        public double getClosedPrice() { return closedPrice; }
        public String getStatus() { return status; }
    }
}