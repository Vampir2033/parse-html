package com.simbirsoft.parsehtml.entities;

public class InputData {
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
