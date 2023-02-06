package com.example.loanmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.models.Approve;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedLoansActivity extends AppCompatActivity {
    TextView name, amount, description, status, date;

    Button approve;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_loans);

        name = findViewById(R.id.rejectName);
        amount = findViewById(R.id.rejectAmount);
        description = findViewById(R.id.rejectDesc);
        status = findViewById(R.id.rejectStatus);
        approve = findViewById(R.id.rejectaprovebtn);
        relativeLayout = findViewById(R.id.rejectBtnLayout);
        date = findViewById(R.id.approvedate);


        name.setText(getIntent().getExtras().getString("name"));
        amount.setText(getIntent().getExtras().getString("amount"));
        description.setText(getIntent().getExtras().getString("description"));
        status.setText(getIntent().getExtras().getString("status"));
        date.setText(getIntent().getExtras().getString("date"));


        approve.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?");
            builder.setTitle("Approve");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                ApiClient.getApiClient().approveLoan(getIntent().getExtras().getString("loan_id")).enqueue(new Callback<Approve>() {
                    @Override
                    public void onResponse(Call<Approve> call, Response<Approve> response) {
                        if (response.isSuccessful() && response.body() != null){
                            Toast.makeText(ApprovedLoansActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            status.setText("Approved");
                            relativeLayout.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Approve> call, Throwable t) {

                    }
                });
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
            {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
    }
