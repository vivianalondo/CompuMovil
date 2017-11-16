package com.appaccounting.compumovil.projectappaccounting.Pojo;

/**
 * Created by viviana on 16/11/17.
 */

public class CategoryEntrie {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntrie() {
    }

    public CategoryEntrie(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
