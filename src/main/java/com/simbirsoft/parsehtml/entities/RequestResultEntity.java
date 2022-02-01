package com.simbirsoft.parsehtml.entities;

import javax.persistence.*;

@Entity
public class RequestResultEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "request_result_seq")
    Long id;

    @ManyToOne
    DateUrl dateUrl;

    String word;
    Integer amount;

    public RequestResultEntity(String word, Integer amount) {
        this.word = word;
        this.amount = amount;
    }

    public RequestResultEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateUrl getDateUrl() {
        return dateUrl;
    }

    public void setDateUrl(DateUrl dateUrl) {
        this.dateUrl = dateUrl;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
