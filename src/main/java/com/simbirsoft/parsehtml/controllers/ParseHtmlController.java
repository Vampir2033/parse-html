package com.simbirsoft.parsehtml.controllers;

import com.simbirsoft.parsehtml.entities.InputData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class ParseHtmlController {
    @GetMapping
    public String providingInterface(Model model){
        model.addAttribute("inputData", new InputData());
        return "index";
    }

    @PostMapping
    public String parseHtml(@ModelAttribute(value = "inputData")InputData inputData){
        System.out.println(inputData);
        return "index";
    }
}
