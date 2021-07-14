package com.jc.tm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {

    @GetMapping("/welcome")
    public String greeting(Model model) {
        return "welcome";
    }

    @GetMapping("/my")
    public String heelo(Model model) {
        return "my";
    }
}
