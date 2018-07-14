package com.carzis.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseResponse{
    @SerializedName("email")
    @Expose
    protected String email;

    @SerializedName("login")
    @Expose
    protected String login;


    public RegisterResponse() {
    }

    public RegisterResponse(String email, String login) {
        this.email = email;
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public String toString() {
        return "email = " + email + ", login = " + login;
    }
}
