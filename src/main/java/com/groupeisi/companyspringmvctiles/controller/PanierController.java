package com.groupeisi.companyspringmvctiles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanierController {
    private static final Logger logger = LoggerFactory.getLogger(PanierController.class);

    @GetMapping("/public/paniers")
    public String showPanierPage() {
        return "paniers";
    }
}
