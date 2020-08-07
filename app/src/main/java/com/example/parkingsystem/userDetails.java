package com.example.parkingsystem;

public class userDetails {

    String userName, email, phoneNo, pass;

    public userDetails() {
    }

    public userDetails(String userName, String email, String phone, String password) {
        this.userName = userName;
        this.email = email;
        this.phoneNo = phone;
        this.pass = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
