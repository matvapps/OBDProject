package com.carzis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {
    @SerializedName("user_photo")
    @Expose
    private String photoUrl;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("device_id")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("secondName")
    @Expose
    private String secondName;
    @SerializedName("birthday")
    @Expose
    private Timestamp birthday;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("is_payed")
    @Expose
    private Integer isPayed;

    public User() {
    }

    public User(String photoUrl, String password, String email, String firstName, String secondName, Timestamp birthday, String phone, Integer isPayed) {
        this.photoUrl = photoUrl;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.phone = phone;
        this.isPayed = isPayed;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer isPayed() {
        return isPayed;
    }

    public void setPayed(Integer payed) {
        isPayed = payed;
    }
}
