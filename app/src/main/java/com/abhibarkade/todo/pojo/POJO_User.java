package com.abhibarkade.todo.pojo;

import java.io.Serializable;
import java.util.UUID;

public class POJO_User implements Serializable {
    public String id, userName, fullName, phone;

    public POJO_User() {
        this.id = "USER:"+UUID.randomUUID();
        userName = "";
        fullName = "";
        phone = "";
    }

    public POJO_User(String userName, String fullName, String phone) {
        this.id = "USER:"+UUID.randomUUID();
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "POJO_User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
