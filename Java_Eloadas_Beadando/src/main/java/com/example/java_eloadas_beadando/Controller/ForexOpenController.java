package com.example.java_eloadas_beadando.Controller;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.*;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ForexOpenController {

    private static final String API_URL = "https://api-fxpractice.oanda.com";
    private static final String ACCESS_TOKEN = "02111978fe023333a51c5a2d558825eb-e460c15f3f86b734e333e94e1032e819";
    private static final String ACCOUNT_ID = "101-004-37603372-002";

    @PostMapping("/forex-open")
    public Map<String, String> openPosition(@RequestBody Map<String, String> req) {

        String instrument = req.get("instrument");
        int units = Integer.parseInt(req.get("units"));

        try {
            Context ctx = new ContextBuilder(API_URL)
                    .setToken(ACCESS_TOKEN)
                    .setApplication("ForexOpen")
                    .build();

            OrderCreateRequest orderReq = new OrderCreateRequest(new AccountID(ACCOUNT_ID));

            MarketOrderRequest marketOrder = new MarketOrderRequest();
            marketOrder.setInstrument(new InstrumentName(instrument));
            marketOrder.setUnits(units);
            orderReq.setOrder(marketOrder);

            OrderCreateResponse resp = ctx.order.create(orderReq);

            var respTx = resp.getOrderCreateTransaction();
            var fill = resp.getOrderFillTransaction();

            Map<String, String> result = new LinkedHashMap<>();
            result.put("status", "Sikeres nyitás!");
            result.put("orderId", respTx.getId().toString());
            result.put("price", fill != null ? fill.getPrice().toString() : "N/A");

            return result;

        } catch (Exception e) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("status", "Hiba történt");
            error.put("details", e.getMessage());
            return error;
        }
    }
}
