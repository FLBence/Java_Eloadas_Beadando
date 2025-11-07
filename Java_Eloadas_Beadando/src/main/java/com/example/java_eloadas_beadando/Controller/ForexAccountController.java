package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexAccountService;
import com.oanda.v20.account.AccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ForexAccountController {

    @Autowired
    private ForexAccountService forexAccountService;

    @GetMapping("/forex-account")
    public String getAccountInfo(Model model) {
        try {
            AccountSummary summary = forexAccountService.getAccountSummary();
            model.addAttribute("summary", summary);
            return "forex-account"; // HTML sablon neve (templates/forex-account.html)
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt a számlaadatok lekérdezésekor: " + e.getMessage());
            return "forex-account";
        }
    }
}