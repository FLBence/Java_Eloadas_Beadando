package com.example.java_eloadas_beadando;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soapclient.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SoapController {

    private static final List<String> DEFAULT_CURRENCIES = Arrays.asList(
            "EUR", "USD", "GBP", "JPY", "CHF", "AUD", "CAD"
    );

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("param", new MessagePrice());
        model.addAttribute("currencies", DEFAULT_CURRENCIES);
        return "index";
    }

    @PostMapping("/")
    public String postSoap(@ModelAttribute MessagePrice param, Model model) {

        model.addAttribute("param", param);
        model.addAttribute("currencies", DEFAULT_CURRENCIES);

        try {
            MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
            MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

            String raw = service.getExchangeRates(
                    param.getStartDate(),
                    param.getEndDate(),
                    param.getCurrency()
            );

            // dátumok
            Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher d = datePattern.matcher(raw);
            List<String> labels = new ArrayList<>();
            while (d.find()) labels.add(d.group());

            // számok
            Pattern pricePattern = Pattern.compile("([0-9]+[.,][0-9]+)");
            Matcher p = pricePattern.matcher(raw);
            List<Double> rates = new ArrayList<>();
            while (p.find()) {
                try {
                    rates.add(Double.parseDouble(p.group().replace(',', '.')));
                } catch (Exception ignore) {}
            }

            int count = Math.min(labels.size(), rates.size());
            labels = labels.subList(0, count);
            rates = rates.subList(0, count);

            model.addAttribute("labels", labels);
            model.addAttribute("rates", rates);


            ObjectMapper mapper = new ObjectMapper();
            model.addAttribute("labelsAsJson", mapper.writeValueAsString(labels));
            model.addAttribute("ratesAsJson", mapper.writeValueAsString(rates));


            model.addAttribute("scrollTo", "soap");

        } catch (Exception e) {
            model.addAttribute("error", "SOAP hiba: " + e.getMessage());
        }

        return "index";
    }
}
