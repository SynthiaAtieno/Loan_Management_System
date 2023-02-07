package com.example.loanmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.ApplyLoan;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyLoanActivity extends AppCompatActivity {
    View view;
    Button apply;
    TextInputLayout amounttxt, descriptiontxt;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    AppConfig appConfig;
    ApiResponse apiResponse = new ApiResponse();
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_loan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        amounttxt = findViewById(R.id.amount);
        descriptiontxt = findViewById(R.id.description);
        appConfig = new AppConfig(this);
        alertDialog = new AlertDialog.Builder(this).create();

        linearLayout = findViewById(R.id.linear_layout);

        apply = findViewById(R.id.apply_button);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyLoan();
            }
        });


    }

    private void applyLoan() {


        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        String amount, description;
        amount = amounttxt.getEditText().getText().toString();
        description = descriptiontxt.getEditText().getText().toString();

        if (amount.isEmpty() && description.isEmpty()) {
            amounttxt.getEditText().setError("Please enter amount");
            amounttxt.getEditText().requestFocus();
        } else if (description.isEmpty()) {
            descriptiontxt.getEditText().setError("Please give reason for loan application");
            descriptiontxt.requestFocus();
        } else {
            ApiClient.getApiClient().applyLoan(amount, description, appConfig.getUserId(), 1).enqueue(new Callback<ApplyLoan>() {
                @Override
                public void onResponse(Call<ApplyLoan> call, Response<ApplyLoan> response) {
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("ok")) {
                            alertDialog.setTitle("Successful");
                            alertDialog.setMessage(response.body().getMessage());
                            alertDialog.show();
                            amounttxt.getEditText().setText("");
                            descriptiontxt.getEditText().setText("");
                        } else if (response.body().getStatus().equals("failed")) {
                            alertDialog.setTitle("Failed");
                            alertDialog.setMessage(response.body().getMessage());
                            alertDialog.show();
                            amounttxt.getEditText().setText("");
                            descriptiontxt.getEditText().setText("");
                        } else {
                            alertDialog.setTitle("No data");
                            alertDialog.setMessage(response.body().getMessage());
                            alertDialog.show();
                            amounttxt.getEditText().setText("");
                            descriptiontxt.getEditText().setText("");
                        }

                    } else {
                        alertDialog.setTitle("Un Successful");
                        alertDialog.setMessage("Response not successful");
                        alertDialog.show();
                        amounttxt.getEditText().setText("");
                        descriptiontxt.getEditText().setText("");
                    }
                }
                //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                @Override
                public void onFailure(Call<ApplyLoan> call, Throwable t) {
                    alertDialog.setTitle("Error Occurred");
                    alertDialog.setMessage(t.getMessage());
                    alertDialog.show();
                    //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}