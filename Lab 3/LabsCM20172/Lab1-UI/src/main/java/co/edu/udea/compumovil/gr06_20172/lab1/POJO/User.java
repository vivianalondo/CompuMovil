
package co.edu.udea.compumovil.gr06_20172.lab1.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("pass")
    @Expose
    private Object pass;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPass() {
        return pass;
    }

    public void setPass(Object pass) {
        this.pass = pass;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    //Constructor vacío
    public User() {
    }

    //Constructor con datos
    public User(String email, String name, Object lastName, Object gender, Object date,
                Object address, Object pass, String city, Object phone) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.date = date;
        this.address = address;
        this.pass = pass;
        this.city = city;
        this.phone = phone;
    }
}
