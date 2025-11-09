package com.example.java_eloadas_beadando.Service;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForexHistoryService {


    private static final String URL = "https://api-fxpractice.oanda.com";
    private static final String TOKEN = "aa1995990e625e7188557f4c7a5f9b0d-614a67cdaecdeb9a2a3290356550b1cf";

    public List<Map<String, String>> getHistory(String instrument, String granularity) {

        try {

            Context ctx = new ContextBuilder(URL)
                    .setToken(TOKEN)
                    .setApplication("HistorikusAdatok")
                    .build();


            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(instrument));
            request.setGranularity(CandlestickGranularity.valueOf(granularity));
            request.setCount(10L); // 10 db adat

            InstrumentCandlesResponse resp = ctx.instrument.candles(request);

            List<Map<String, String>> result = new ArrayList<>();


            for (Candlestick candle : resp.getCandles()) {
                Map<String, String> row = new HashMap<>();
                row.put("time", candle.getTime().toString());
                row.put("price", candle.getMid().getC().toString());
                result.add(row);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
