package com.hypersrot.assignment_hypersrot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/payment")
    public String payment(){
        return "payment";
    }
}
