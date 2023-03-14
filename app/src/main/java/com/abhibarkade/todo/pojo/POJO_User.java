package com.abhibarkade.todo.pojo;

import java.io.Serializable;
import java.util.UUID;

public class POJO_User implements Serializable {
    public String id, username, fullName, phone, password;

    public POJO_User() {
        this.id = "USER:" + UUID.randomUUID();
        username = "";
        fullName = "";
        phone = "";
        password = "";
    }

    public POJO_User(String username, String fullName, String password, String phone) {
        this.id = "USER:" + UUID.randomUUID();
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "POJO_User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
