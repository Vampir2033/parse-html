package com.simbirsoft.parsehtml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParseHtmlController {
    @GetMapping
    public String providingInterface(){
        return "index";
    }
}
