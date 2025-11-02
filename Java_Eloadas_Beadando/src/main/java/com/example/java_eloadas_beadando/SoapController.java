package com.example.java_eloadas_beadando;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapImpl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SoapController {
    private static final List<String> DEFAULT_CURRENCIES = Arrays.asList(
            "EUR", "USD", "GBP", "JPY", "CHF", "AUD", "CAD"
    );

    @GetMapping("/soap")
    public String getSoapPage(Model model) {
        model.addAttribute("param", new MessagePrice());
        model.addAttribute("currencies", DEFAULT_CURRENCIES);
        return "soap"; // ugyanaz az oldal, ahol a grafikon is van
    }

    @PostMapping("/soap")
    public String postSoap(@ModelAttribute MessagePrice param, Model model) {
        model.addAttribute("param", param);
        model.addAttribute("currencies", DEFAULT_CURRENCIES);

        try {
            MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
            MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

            String raw = service.getExchangeRates(param.getStartDate(), param.getEndDate(), param.getCurrency());
            model.addAttribute("rawResponse", raw);

            // egyszerű XML vagy CSV adat kinyerése
            List<String> labels = new ArrayList<>();
            List<Double> rates = new ArrayList<>();

            Pattern datePattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
            Matcher dm = datePattern.matcher(raw);
            List<String> foundDates = new ArrayList<>();
            while (dm.find()) foundDates.add(dm.group(1));

            Pattern numPattern = Pattern.compile("([0-9]+[.,][0-9]+)");
            Matcher nm = numPattern.matcher(raw);
            List<Double> foundRates = new ArrayList<>();
            while (nm.find()) {
                try {
                    foundRates.add(Double.parseDouble(nm.group(1).replace(',', '.')));
                } catch (Exception ignored) {}
            }

            int count = Math.min(foundDates.size(), foundRates.size());
            for (int i = 0; i < count; i++) {
                labels.add(foundDates.get(i));
                rates.add(foundRates.get(i));
            }

            model.addAttribute("labels", labels);
            model.addAttribute("rates", rates);

        } catch (Exception e) {
            model.addAttribute("error", "SOAP hiba: " + e.getMessage());
            e.printStackTrace();
        }

        return "soap";
    }
}
