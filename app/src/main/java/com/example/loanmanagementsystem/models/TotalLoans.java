package com.example.loanmanagementsystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalLoans {
    @SerializedName("total")
    @Expose
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
