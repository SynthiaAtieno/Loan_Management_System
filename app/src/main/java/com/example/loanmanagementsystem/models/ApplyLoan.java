package com.example.loanmanagementsystem.models;

import com.google.gson.annotations.SerializedName;

public class ApplyLoan {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    private String user_id;
    private String status_id;

    public String getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus_id() {
        return status_id;
    }
}
