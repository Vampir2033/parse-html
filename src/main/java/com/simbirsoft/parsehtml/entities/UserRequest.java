package com.simbirsoft.parsehtml.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "requests")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Time time;

    private String url;

    private String delimiters;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    List<RequestResult> results = new LinkedList<>();

    public UserRequest(Date date, Time time, String url, String delimiters) {
        this.date = date;
        this.time = time;
        this.url = url;
        this.delimiters = delimiters;
    }

    public UserRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public List<RequestResult> getResults() {
        return results;
    }

    public void setResults(List<RequestResult> results) {
        results.forEach(r -> r.setRequest(this));
        this.results = results;
    }

}
