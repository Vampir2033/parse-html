package com.simbirsoft.parsehtml.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

@Entity
public class DateUrl {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "date_url_seq")
    private Long id;

    private Date date;

    private Time time;

    private String url;

    private String delimiters;

    public Long getId() {
        return id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public DateUrl(Date date, Time time, String url, String delimiters) {
        this.date = date;
        this.time = time;
        this.url = url;
        this.delimiters = delimiters;
    }

    public DateUrl() {
    }

    @Override
    public String toString() {
        return "DateUrl{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", url='" + url + '\'' +
                ", delimiters='" + delimiters + '\'' +
                '}';
    }
}
