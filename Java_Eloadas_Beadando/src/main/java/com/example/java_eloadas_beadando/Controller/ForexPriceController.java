package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForexPriceController {

    private final ForexPriceService forexPriceService;

    @Autowired
    public ForexPriceController(ForexPriceService forexPriceService) {
        this.forexPriceService = forexPriceService;
    }
    @GetMapping("/forex-price")
    public String showPricePage(){
        return "redirect:/#forex-price";
    }
    @PostMapping("/forex-price")
    public String getPrice(@RequestParam String instrument, RedirectAttributes redirectAttributes) {
        double price = forexPriceService.getCurrentPrice(instrument);
        redirectAttributes.addFlashAttribute("instrument", instrument);
        redirectAttributes.addFlashAttribute("price", price);
        return "redirect:/#forex-price";
    }
}
