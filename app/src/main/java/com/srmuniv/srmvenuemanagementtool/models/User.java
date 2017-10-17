package com.srmuniv.srmvenuemanagementtool.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eesh on 10/14/17.
 */

public class User {

    @SerializedName("_id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("department")
    String department;

    @SerializedName("type")
    String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User (String name, String email, String department, String role) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.role = role;
    }


}
