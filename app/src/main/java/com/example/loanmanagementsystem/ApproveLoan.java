package com.example.loanmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.activities.MainActivity;
import com.example.loanmanagementsystem.activities.SignIn;
import com.example.loanmanagementsystem.adminFragments.InProrgressLoan;
import com.example.loanmanagementsystem.models.Approve;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveLoan extends AppCompatActivity {
    TextView name, amount, desc, status, loan_id;
    Button approve, reject;

    RelativeLayout btnlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_loan);

        name = findViewById(R.id.approveName);
        amount = findViewById(R.id.approveAmount);
        desc = findViewById(R.id.approveDesc);
        status = findViewById(R.id.approveStatus);
        //loan_id = findViewById(R.id.loan_id_pending);
        btnlayout = findViewById(R.id.btnlayout);


        approve = findViewById(R.id.approvebtn);
        reject = findViewById(R.id.rejectbtn);

        name.setText(getIntent().getExtras().getString("name"));
        amount.setText(getIntent().getExtras().getString("amount"));
        desc.setText(getIntent().getExtras().getString("description"));
        status.setText(getIntent().getExtras().getString("status"));

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApproveLoan.this);
                builder.setMessage("Are you sure?");
                builder.setTitle("Approve");
                builder.setCancelable(false);
//                builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) ->{
//                    Toast.makeText(ApproveLoan.this, "Logged out successful", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ApproveLoan.this, InProrgressLoan.class));
//                    finish();
//                });
//                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
//                {
//                    dialog.cancel();
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                ApiClient.getApiClient().approveLoan(getIntent().getExtras().getString("loanId")).enqueue(new Callback<Approve>() {
                    @Override
                    public void onResponse(Call<Approve> call, Response<Approve> response) {
                        if (response.isSuccessful() && response.body() != null){
                            builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) ->{
                                Toast.makeText(ApproveLoan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                status.setText("Approved");
                                btnlayout.setVisibility(View.INVISIBLE);
                            });
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
                            {
                                dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else {
                            Toast.makeText(ApproveLoan.this, "Request not successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Approve> call, Throwable t) {
                        Toast.makeText(ApproveLoan.this, "Error occurred"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApproveLoan.this);
                builder.setMessage("Are you sure?");
                builder.setTitle("Reject");
                builder.setCancelable(false);
                ApiClient.getApiClient().rejectLoan(getIntent().getExtras().getString("loanId")).enqueue(new Callback<Approve>() {
                    @Override
                    public void onResponse(Call<Approve> call, Response<Approve> response) {
                        if (response.isSuccessful() && response.body() != null){
                            builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) ->{
                                Toast.makeText(ApproveLoan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                status.setText("Rejected");
                                btnlayout.setVisibility(View.INVISIBLE);
                            });
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
                            {
                                dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                        else {
                            Toast.makeText(ApproveLoan.this, "Request not successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Approve> call, Throwable t) {
                        Toast.makeText(ApproveLoan.this, "Error occurred"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}