package com.codegym.webservice.payload.request;


public class PostSearchRequest {
    private Long categoryId;
    private Long regionId;
    private Long postTypeId;
    private Boolean condition;
    private Double area;
    private Long price;
    private Boolean deal;
    private Long directionId;
    private String keyword;
    private Boolean customerType;
    private String year;
    private Boolean gender;

    public PostSearchRequest() {
    }

    public PostSearchRequest(Long categoryId, Long regionId, Long postTypeId, Boolean condition, Double area, Long price, Boolean deal, Long directionId, String keyword, Boolean customerType, String year, Boolean gender) {
        this.categoryId = categoryId;
        this.regionId = regionId;
        this.postTypeId = postTypeId;
        this.condition = condition;
        this.area = area;
        this.price = price;
        this.deal = deal;
        this.directionId = directionId;
        this.keyword = keyword;
        this.customerType = customerType;
        this.year = year;
        this.gender = gender;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(Long postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Boolean getCondition() {
        return condition;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getDeal() {
        return deal;
    }

    public void setDeal(Boolean deal) {
        this.deal = deal;
    }

    public Long getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Long directionId) {
        this.directionId = directionId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Boolean customerType) {
        this.customerType = customerType;
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
