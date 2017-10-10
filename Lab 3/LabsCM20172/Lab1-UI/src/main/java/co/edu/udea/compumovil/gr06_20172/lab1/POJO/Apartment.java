
package co.edu.udea.compumovil.gr06_20172.lab1.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apartment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ap_type")
    @Expose
    private String apType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("area")
    @Expose
    private Float area;
    @SerializedName("value")
    @Expose
    private Float value;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("picture")
    @Expose
    private String picture;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApType() {
        return apType;
    }

    public void setApType(String apType) {
        this.apType = apType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Apartment(){

    }

    public Apartment(int userId, String name, String apType, String description,
                     Float area, Float value, String address, String picture){

        this.userId = userId;
        this.name = name;
        this. apType = apType;
        this.description = description;
        this.area = area;
        this.value = value;
        this.address = address;
        this.picture = picture;

    }

}
