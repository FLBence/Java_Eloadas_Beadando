package com.example.java_eloadas_beadando.Service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Service
public class ForexPriceService {

    private static final String API_URL = "https://api-fxpractice.oanda.com/v3/accounts/";
    private static final String ACCOUNT_ID = "101-004-37623364-001";
    private static final String AUTH_TOKEN = "aa1995990e625e7188557f4c7a5f9b0d-614a67cdaecdeb9a2a3290356550b1cf";

    public double getCurrentPrice(String instrument) {
        try {
            String url = API_URL + ACCOUNT_ID + "/pricing?instruments=" + instrument;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.lines().collect(Collectors.joining());
            in.close();

            JSONObject json = new JSONObject(response);
            JSONObject priceObj = json.getJSONArray("prices").getJSONObject(0);
            double bid = priceObj.getJSONArray("bids").getJSONObject(0).getDouble("price");
            double ask = priceObj.getJSONArray("asks").getJSONObject(0).getDouble("price");

            return (bid + ask) / 2.0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}