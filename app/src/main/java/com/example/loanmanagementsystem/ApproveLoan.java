package com.example.loanmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                builder.setTitle("Reject");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    ApiClient.getApiClient().approveLoan(getIntent().getExtras().getString("loanId")).enqueue(new Callback<Approve>() {
                        @Override
                        public void onResponse(Call<Approve> call, Response<Approve> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(ApproveLoan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                status.setText("Approved");
                                btnlayout.setVisibility(View.INVISIBLE);
                                startAlertDialog();
                            } else {
                                Toast.makeText(ApproveLoan.this, "Error occurred" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Approve> call, Throwable t) {
                            Toast.makeText(ApproveLoan.this, "Error occurred" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ApproveLoan.this);
                builder.setMessage("Are you sure?");
                builder.setTitle("Reject");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    ApiClient.getApiClient().rejectLoan(getIntent().getExtras().getString("loanId")).enqueue(new Callback<Approve>() {
                        @Override
                        public void onResponse(Call<Approve> call, Response<Approve> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(ApproveLoan.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                status.setText("Rejected");
                                btnlayout.setVisibility(View.INVISIBLE);
                                startAlertDialogReject();
                            }
                        }

                        @Override
                        public void onFailure(Call<Approve> call, Throwable t) {
                            Toast.makeText(ApproveLoan.this, "Error occurred" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
                {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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

    private void startAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.approvedmessage, null);
        alert.setView(view);
        final  AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        AppCompatButton close = view.findViewById(R.id.close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void startAlertDialogReject() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.rejectedmessage, null);
        alert.setView(view);
        final  AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        AppCompatButton close = view.findViewById(R.id.close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}