package com.codegym.webservice.payload.request;

public class PostSearchByYearRequest {
    private String year;
    private Boolean gender;

    public PostSearchByYearRequest() {
    }

    public PostSearchByYearRequest(String year, Boolean gender) {
        this.year = year;
        this.gender = gender;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
