package com.example.loanmanagementsystem.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("status")
    private  String status;
    @SerializedName("message")
    private  String message;
    @SerializedName("username")
    private String name;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
