package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ForexPositionController {

    private final ForexTradeService forexTradeService;

    @Autowired
    public ForexPositionController(ForexTradeService forexTradeService) {
        this.forexTradeService = forexTradeService;
    }

    @GetMapping("/forex-positions")
    public List<TradeDto> listPositions() {


        return forexTradeService.getOpenTrades().entrySet().stream()
                .filter(x -> !x.getValue().isClosed())
                .map(x -> new TradeDto(
                        x.getValue().getTradeId(),
                        x.getValue().getInstrument(),
                        x.getValue().getUnits(),
                        x.getValue().getOpenPrice()
                ))
                .collect(Collectors.toList());
    }

    // --- DTO ---
    public static class TradeDto {
        private long tradeId;
        private String instrument;
        private double units;
        private double price;

        public TradeDto(long tradeId, String instrument, double units, double price) {
            this.tradeId = tradeId;
            this.instrument = instrument;
            this.units = units;
            this.price = price;
        }

        public long getTradeId() { return tradeId; }
        public String getInstrument() { return instrument; }
        public double getUnits() { return units; }
        public double getPrice() { return price; }
    }
}