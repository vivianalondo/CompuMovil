package com.appaccounting.compumovil.projectappaccounting.Pojo;

/**
 * Created by viviana on 16/11/17.
 */

public class Budget {
    private Integer id;
    private Double amount;
    private String description;
    private String startDate;
    private String endDate;
    private Integer userId;
    private Integer categoryDebit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryDebit() {
        return categoryDebit;
    }

    public void setCategoryDebit(Integer categoryDebit) {
        this.categoryDebit = categoryDebit;
    }

    public Budget() {
    }

    public Budget(Double amount, String description, String startDate, String endDate) {
        this.amount = amount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
