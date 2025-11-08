package com.example.java_eloadas_beadando.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForexTradeService {

    private final ForexPriceService forexPriceService;

    // Ez egy egyszerű memória-alapú tároló (demo)
    private final Map<Long, Trade> openTrades = new HashMap<>();

    @Autowired
    public ForexTradeService(ForexPriceService forexPriceService) {
        this.forexPriceService = forexPriceService;

        // Demo adat: nyitott trade
        openTrades.put(1L, new Trade(1L, "EUR/USD", 1000, 1.0760, false, 0.0));
        openTrades.put(2L, new Trade(2L, "GBP/USD", 2000, 1.2510, false, 0.0));
    }

    // --- Nyitás (korábbi funkcióhoz) ---
    public Trade openTrade(String instrument, double units) {
        long id = openTrades.size() + 1;
        double openPrice = forexPriceService.getCurrentPrice(instrument);
        Trade t = new Trade(id, instrument, units, openPrice, false, 0.0);
        openTrades.put(id, t);
        return t;
    }

    // --- Zárás piaci áron ---
    public Trade closeTrade(long tradeId) {
        Trade trade = openTrades.get(tradeId);
        if (trade == null || trade.isClosed()) return null;

        double currentPrice = forexPriceService.getCurrentPrice(trade.getInstrument());
        trade.setClosedPrice(currentPrice);
        trade.setClosed(true);

        System.out.println("Trade #" + tradeId + " lezárva piaci áron: " + currentPrice);
        return trade;
    }

    // --- Segéd belső osztály ---
    public static class Trade {
        private long tradeId;
        private String instrument;
        private double units;
        private double openPrice;
        private boolean closed;
        private double closedPrice;

        public Trade(long tradeId, String instrument, double units, double openPrice, boolean closed, double closedPrice) {
            this.tradeId = tradeId;
            this.instrument = instrument;
            this.units = units;
            this.openPrice = openPrice;
            this.closed = closed;
            this.closedPrice = closedPrice;
        }

        public long getTradeId() { return tradeId; }
        public String getInstrument() { return instrument; }
        public double getUnits() { return units; }
        public double getOpenPrice() { return openPrice; }
        public boolean isClosed() { return closed; }
        public double getClosedPrice() { return closedPrice; }

        public void setClosed(boolean closed) { this.closed = closed; }
        public void setClosedPrice(double closedPrice) { this.closedPrice = closedPrice; }
    }
}