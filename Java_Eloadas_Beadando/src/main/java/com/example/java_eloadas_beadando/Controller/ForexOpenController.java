package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForexOpenController {

    private final ForexTradeService forexTradeService;

    @Autowired
    public ForexOpenController(ForexTradeService forexTradeService) {
        this.forexTradeService = forexTradeService;
    }

    @PostMapping("/forex-open")
    public OpenResponse openTrade(@RequestBody OpenRequest request) {
        try {
            var trade = forexTradeService.openTrade(request.getInstrument(), request.getUnits());

            return new OpenResponse(
                    "Pozíció sikeresen nyitva!",
                    trade.getTradeId(),
                    trade.getInstrument(),
                    trade.getOpenPrice(),
                    "OK"
            );

        } catch (Exception e) {
            return new OpenResponse(
                    "Hiba történt: " + e.getMessage(),
                    0,
                    request.getInstrument(),
                    0.0,
                    "HIBA"
            );
        }
    }

    // --- DTO-k ---

    public static class OpenRequest {
        private String instrument;
        private int units;

        public String getInstrument() { return instrument; }
        public int getUnits() { return units; }

        public void setInstrument(String instrument) { this.instrument = instrument; }
        public void setUnits(int units) { this.units = units; }
    }

    public static class OpenResponse {
        private String message;
        private long tradeId;
        private String instrument;
        private double openPrice;
        private String status;

        public OpenResponse(String message, long tradeId, String instrument, double openPrice, String status) {
            this.message = message;
            this.tradeId = tradeId;
            this.instrument = instrument;
            this.openPrice = openPrice;
            this.status = status;
        }

        public String getMessage() { return message; }
        public long getTradeId() { return tradeId; }
        public String getInstrument() { return instrument; }
        public double getOpenPrice() { return openPrice; }
        public String getStatus() { return status; }
    }
}
