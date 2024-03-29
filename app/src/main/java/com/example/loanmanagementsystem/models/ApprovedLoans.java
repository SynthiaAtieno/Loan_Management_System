package com.example.loanmanagementsystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovedLoans {


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fullname")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("loan_id")
    @Expose
    private String loanId;

    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
