package com.simbirsoft.parsehtml.services;

import com.simbirsoft.parsehtml.entities.RequestResult;
import com.simbirsoft.parsehtml.entities.UserRequest;
import com.simbirsoft.parsehtml.repositories.UserRequestRepository;
import com.simbirsoft.parsehtml.services.exceptions.IncorrectDelimitersException;
import com.simbirsoft.parsehtml.services.exceptions.UnreachableUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParseHtmlService {

    @Autowired
    UserRequestRepository userRequestRepository;

    Logger logger = LoggerFactory.getLogger(ParseHtmlService.class);

    public Map<String, Integer> parseHtml(String url, String delimiters)
            throws UnreachableUrlException, IncorrectDelimitersException{
        String pageText;
        try {
            pageText = WebPage.getWebPageText(url);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке странички по url '" + url + "'", e);
            throw new UnreachableUrlException(e);
        }
        Map<String, Integer> wordsOnPage;
        try {
            wordsOnPage = SplitText.countWordsOnPage(pageText, delimiters);
        } catch (IllegalArgumentException e){
            logger.error("Ошибка при разделении странички на слова. Переданный список разделителей: "
                    + delimiters, e);
            throw new IncorrectDelimitersException(e);
        }
        try {
            saveToRepository(url, delimiters, wordsOnPage);
        } catch (Exception e){
            logger.error("Ошибка при записи статистики в БД", e);
            throw e;
        }
        return wordsOnPage;
    }

    private void saveToRepository(String url, String delimiters, Map<String, Integer> results){
        UserRequest userRequest = new UserRequest(
                new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                url,
                delimiters);
        userRequestRepository.save(userRequest);

        userRequest.setResults(results.entrySet().stream()
                .map(s -> new RequestResult(s.getKey(), s.getValue(), userRequest))
                .collect(Collectors.toList()));
        userRequestRepository.save(userRequest);

    }
}
