package com.simbirsoft.parsehtml.controllers;

import com.simbirsoft.parsehtml.services.ParseHtmlService;
import com.simbirsoft.parsehtml.services.exceptions.IncorrectDelimitersException;
import com.simbirsoft.parsehtml.services.exceptions.UnreachableUrlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class ParseHtmlController {

    @Autowired
    ParseHtmlService parseHtmlService;

    @GetMapping("/index")
    public String providingInterface(Model model){
        model.addAttribute("inputData", new InputData());
        return "index";
    }

    @PostMapping("/index")
    public RedirectView parseHtml(@ModelAttribute(value = "inputData")InputData inputData,
                                  RedirectAttributes attributes){
        try {
            Map<String, Integer> words = parseHtmlService.parseHtml(inputData.getUrl(), inputData.getDelimiters());
            attributes.addFlashAttribute("wordsMap", words);
            return new RedirectView("/result-page");
        } catch (UnreachableUrlException e){
            attributes.addFlashAttribute("errDescription",
                    "Не удалось получить доступ к сайту '" + inputData.getUrl() + "'");
            return new RedirectView("/user-error");
        } catch (IncorrectDelimitersException e){
            attributes.addFlashAttribute("errDescription",
                    "Некорректный список разделителей " + inputData.getDelimiters());
            return new RedirectView("/user-error");
        } catch (Exception e){
            attributes.addFlashAttribute("errDescription", "Неизвестная ошибка");
            return new RedirectView("/user-error");
        }
    }

    @GetMapping("/user-error")
    public String errorPage(Model model,
                            @ModelAttribute("errDescription")String errDescription){
        model.addAttribute("errDescription", errDescription);
        return "error_page";
    }

    @GetMapping("/result-page")
    public String resultPage(Model model,
                             @ModelAttribute("wordsMap")Map<String, Integer> wordsMap){
        model.addAttribute("wordsOnPage", wordsMap);
        return "result_page";
    }

}

class InputData {
    private String url;
    private String delimiters;

    public InputData() {
        url = "";
        delimiters = "";
    }

    public InputData(String url, String delimiters) {
        this.url = url;
        this.delimiters = delimiters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDelimiters() {
        return delimiters;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

}
