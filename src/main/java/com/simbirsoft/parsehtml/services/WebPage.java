package com.simbirsoft.parsehtml.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebPage {
    public static String getWebPageText(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.body().text();
    }
}
