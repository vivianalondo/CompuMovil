package com.appaccounting.compumovil.projectappaccounting.Pojo;

import com.appaccounting.compumovil.projectappaccounting.IngresosActivity;

/**
 * Created by viviana on 20/11/17.
 */

public class Transaction {

    private Integer id;
    private Integer userId;
    private Double amount;
    private String description;
    private String date;
    private Integer category;
    private int typeTransaction;

    public Transaction() {
    }

    public Transaction(Integer userId, Double amount, String description, String date, Integer category, int typeTransaction) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
        this.typeTransaction = typeTransaction;
    }

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public int getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}
