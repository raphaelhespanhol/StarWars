package com.raphahes.starwars.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// inject via application.properties
    @Value("${service.app.name}")
    private String appName;
	
    @Value("${service.app.version}")
    private String appVersion;
	
    @Value("${service.app.about}")
    private String appAbout;
    
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("appAbout", appAbout);
        
        return "index"; //view
    }
}
