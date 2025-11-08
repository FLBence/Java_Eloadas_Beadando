package com.example.java_eloadas_beadando.Controller;

import com.example.java_eloadas_beadando.Service.ForexHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ForexHistoryRestController {

    private final ForexHistoryService service;

    public ForexHistoryRestController(ForexHistoryService service) {
        this.service = service;
    }

    @GetMapping("/historyPrice")
    public List<Map<String, String>> getHistory(
            @RequestParam String instrument,
            @RequestParam String granularity) {

        return service.getHistory(instrument, granularity);
    }
}
