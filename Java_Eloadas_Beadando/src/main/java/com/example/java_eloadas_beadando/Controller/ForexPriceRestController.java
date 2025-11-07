package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForexPriceRestController {

    private final ForexPriceService forexPriceService;

    @Autowired
    public ForexPriceRestController(ForexPriceService forexPriceService) {
        this.forexPriceService = forexPriceService;
    }

    @GetMapping("/price")
    public double getPrice(@RequestParam String instrument) {
        return forexPriceService.getCurrentPrice(instrument);
    }
}