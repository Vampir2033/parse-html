package com.simbirsoft.parsehtml.controllers;

import com.simbirsoft.parsehtml.entities.DateUrl;
import com.simbirsoft.parsehtml.entities.RequestResultEntity;
import com.simbirsoft.parsehtml.repositories.DataUrlRepository;
import com.simbirsoft.parsehtml.services.SplitText;
import com.simbirsoft.parsehtml.services.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ParseHtmlController {

    @Autowired
    DataUrlRepository dataUrlRepository;

    Logger logger = LoggerFactory.getLogger(ParseHtmlController.class);
    @GetMapping("/index")
    public String providingInterface(Model model){
        model.addAttribute("inputData", new InputData());
        return "index";
    }

    @PostMapping("/index")
    public RedirectView parseHtml(@ModelAttribute(value = "inputData")InputData inputData,
                                  RedirectAttributes attributes){
        String pageText = null;
        try {
            pageText = WebPage.getWebPageText(inputData.getUrl());
        } catch (Exception e) {
            logger.error("Ошибка при загрузке странички по url '" + inputData.getUrl() + "'", e);
            attributes.addFlashAttribute("errDescription",
                    "Не удалось получить доступ к сайту '" + inputData.getUrl() + "'");
            return new RedirectView("/user-error");
        }
        Map<String, Integer> wordsOnPage = null;
        try {
            wordsOnPage = SplitText.countWordsOnPage(pageText, inputData.getDelimiters());
        } catch (IllegalArgumentException e){
            logger.error("Ошибка при разделении странички на слова. Переданный список разделителей: "
                    + inputData.getDelimiters(), e);
            attributes.addFlashAttribute("errDescription",
                    "Некорректный список разделителей " + inputData.getDelimiters());
            return new RedirectView("/user-error");
        }
        attributes.addFlashAttribute("wordsMap", wordsOnPage);
        DateUrl dateUrlEntity = new DateUrl(
                new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                inputData.getUrl(),
                inputData.getDelimiters());
        dataUrlRepository.save(dateUrlEntity);
        dateUrlEntity.setResults(wordsOnPage.entrySet().stream()
                .map(s -> new RequestResultEntity(s.getKey(), s.getValue()))
                .collect(Collectors.toList()));
        dataUrlRepository.save(dateUrlEntity);
        System.out.println(dateUrlEntity);
        return new RedirectView("/result-page");
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

    @Override
    public String toString() {
        return "InputData{" +
                "url='" + url + '\'' +
                ", delimiters='" + delimiters + '\'' +
                '}';
    }
}
