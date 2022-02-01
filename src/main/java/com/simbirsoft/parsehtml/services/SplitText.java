package com.simbirsoft.parsehtml.services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.jsoup.internal.StringUtil;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SplitText {
    public static Map<String, Integer> countWordsOnPage(String pageText, String delimiters) throws IllegalArgumentException{
        // сначала - проверка списка разделителей
        if(!checkInputDelimiters(delimiters))
            throw new IllegalArgumentException("Недопустимый список разделителей");
        String strDelimiters = getDelimitersFromJson(delimiters.trim());
        Map<String, Integer> result = new HashMap<>();
        String[] words = null;
        String regexForPage = null;
        try {
            regexForPage = "["
                    + QueryParser.escape(strDelimiters)     // экранируем спецсимволы
                    + "]+";
            words = pageText.split(regexForPage);
        } catch (PatternSyntaxException e){
            throw new IllegalArgumentException(
                    "Не удалось разделить текст полученным регулярным выражением [" + regexForPage);
        }
        Arrays.stream(words)
                .filter(s -> !s.equals(""))
                .map(String::toUpperCase)
                .forEach(s -> result.put(s, result.getOrDefault(s, 0)+1));
        return result;
    }

    private static String getDelimitersFromJson(String delimiters){
        // приведение строки к JSON формату
        StringBuilder stringBuilder = new StringBuilder(delimiters.trim());
        stringBuilder.replace(0, 1, "[");
        stringBuilder.replace(stringBuilder.length()-1, stringBuilder.length(), "]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        try{
            List<String> symbols = gson.fromJson(stringBuilder.toString(), type);
            return StringUtil.join(symbols, "");
        } catch(JsonSyntaxException e){
            throw new IllegalArgumentException("Список разделителей прошёл проверку регулярным выражением, " +
                    "но преобразовать его в строку разделителей не удалось");
        }
    }

    private static boolean checkInputDelimiters(String delimiters){
        String regex = "^\\s*\\{\\s*('\\\\?.'\\s*,\\s*)*'\\\\?.'\\s*}\\s*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(delimiters).matches();
    }
}