package com.simbirsoft.parsehtml.entities;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class RequestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String word;
    Integer amount;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private UserRequest request;

    public RequestResult(String word, Integer amount, UserRequest request) {
        this.word = word;
        this.amount = amount;
        this.request = request;
    }

    public RequestResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRequest getRequest() {
        return request;
    }

    public void setRequest(UserRequest request) {
        this.request = request;
    }
}
