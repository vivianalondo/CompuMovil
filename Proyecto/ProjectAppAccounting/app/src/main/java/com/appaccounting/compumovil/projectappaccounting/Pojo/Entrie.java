package com.appaccounting.compumovil.projectappaccounting.Pojo;

/**
 * Created by viviana on 15/11/17.
 */

public class Entrie {

    private Integer id;
    private Integer userId;
    private Double amount;
    private String description;
    private String date;
    private Integer categoryDebit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCategoryDebit() {
        return categoryDebit;
    }

    public void setCategoryDebit(Integer categoryDebit) {
        this.categoryDebit = categoryDebit;
    }

    public Entrie(Double amount, String description, String date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Entrie() {
    }
}
