package com.simbirsoft.parsehtml;

import com.simbirsoft.parsehtml.services.SplitText;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SplitTextTest {

    @Test
    void checkDelimiters(){
        String delimiters = null;
        String page = null;

        page = "";
        delimiters = "{' ', ','}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                }});

        assertThrows(IllegalArgumentException.class, () ->
                SplitText.countWordsOnPage(
                        "",
                        "{'--'}"));

        assertThrows(IllegalArgumentException.class, () ->
                SplitText.countWordsOnPage(
                        "",
                        "{'--'}"));

        assertThrows(IllegalArgumentException.class, () ->
                SplitText.countWordsOnPage(
                        "",
                        "{'\\+'}"));

        assertThrows(IllegalArgumentException.class, () ->
                SplitText.countWordsOnPage(
                        "",
                        "{'-',}"));

        assertThrows(IllegalArgumentException.class, () ->
                SplitText.countWordsOnPage(
                        "",
                        "{'-', '+',}"));
    }

    @Test
    void countWordsOnPage() throws IllegalArgumentException {
        String delimiters = null;
        String page = null;

        page = "Раз раз, Два";
        delimiters = "{' ', ','}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 2);
                    put("ДВА", 1);
        }});

        page = "\tРаз раз, Два!";
        delimiters = "{' ', ',', '!', '\t'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 2);
                    put("ДВА", 1);
                }});

        page = "";
        delimiters = "{' ', ',', '!', '\t'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                }});

        page = "\tРаз раз, Два!";
        delimiters = "{' ', ',', '!', '\t'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 2);
                    put("ДВА", 1);
                }});

        page = "Мы_сильны_твоей_волей_монолит";
        delimiters = "{' ', ',', '!', '\t'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("МЫ_СИЛЬНЫ_ТВОЕЙ_ВОЛЕЙ_МОНОЛИТ", 1);
                }});

        page = "Раз[два]";
        delimiters = "{'[', ']'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});

        page = "Раз{два}";
        delimiters = "{'{', '}'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});

        page = "Раз.два";
        delimiters = "{'.'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});

        page = "Раз'два";
        delimiters = "{'\\''}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});

        page = "Раз\nдва";
        delimiters = "{'\\n'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});

        page = "Раз\\два";
        delimiters = "{'\\\\'}";
        assertEquals(SplitText.countWordsOnPage(page, delimiters),
                new HashMap<String, Integer>(){{
                    put("РАЗ", 1);
                    put("ДВА", 1);
                }});
    }
}