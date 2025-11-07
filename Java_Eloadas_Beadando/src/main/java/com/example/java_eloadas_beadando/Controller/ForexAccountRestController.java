package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexAccountService;
import com.oanda.v20.account.AccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forex")
public class ForexAccountRestController {

    @Autowired
    private ForexAccountService forexAccountService;

    @GetMapping("/summary")
    public AccountSummary getAccountSummary() throws Exception {
        return forexAccountService.getAccountSummary();
    }
}