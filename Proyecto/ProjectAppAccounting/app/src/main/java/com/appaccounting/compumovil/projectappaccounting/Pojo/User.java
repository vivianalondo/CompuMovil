package com.appaccounting.compumovil.projectappaccounting.Pojo;

/**
 * Created by viviana on 15/11/17.
 */


public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //Constructor vacío
    public User() {
    }

    //Constructor con datos
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
