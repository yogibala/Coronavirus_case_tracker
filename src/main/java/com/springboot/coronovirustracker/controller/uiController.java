package com.springboot.coronovirustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.coronovirustracker.models.Locationstats;

@Controller
public class uiController {
    @Autowired
    com.springboot.coronovirustracker.services.coronovirussourceget coronovirussourceget;

    @GetMapping("/")
    public String home(Model model) {
        List<Locationstats> allStats = coronovirussourceget.getAllStats();

        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }

}
