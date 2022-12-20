package com.example.loanmanagementsystem.userFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.activities.Admin;
import com.example.loanmanagementsystem.activities.MainActivity;
import com.example.loanmanagementsystem.activities.SignIn;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.ApplyLoan;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;
import com.example.loanmanagementsystem.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApplyLoanFragment extends Fragment {

    View view;
    Button apply;
    TextInputLayout amounttxt, descriptiontxt;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    AppConfig appConfig;
    ApiResponse apiResponse = new ApiResponse();

    public ApplyLoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apply_loan, container, false);

        progressDialog = new ProgressDialog(getContext());
        amounttxt = view.findViewById(R.id.amount);
        descriptiontxt = view.findViewById(R.id.description);
        appConfig = new AppConfig(getContext());

//        if (appConfig.isUserlogin()) {
//            String username = appConfig.getNameOfUser();
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.putExtra("username", username);
//            startActivity(intent);
//        }

        linearLayout = view.findViewById(R.id.linear_layout);

        apply = view.findViewById(R.id.apply_button);

        //txtname.setText(username);
//
//        if (appConfig.isUserlogin()) {
//            String user_id = appConfig.getUserId();
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.putExtra("user_id", user_id);
//            startActivity(intent);
//        }


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //applyLoan();
            }
        });

        return view;
    }

    private void applyLoan() {


        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        String amount, description;
        amount = amounttxt.getEditText().getText().toString();
        description = descriptiontxt.getEditText().getText().toString();

        if (amount.equals("") && description.equals("")) {
            amounttxt.getEditText().setError("Please enter amount");
            amounttxt.getEditText().requestFocus();

            descriptiontxt.getEditText().setError("Please describe why you want the loan");
            descriptiontxt.requestFocus();
        }
        else{
            ApiClient.getApiClient().applyLoan(amount, description,apiResponse.getUserId(),1).enqueue(new Callback<ApplyLoan>() {
                @Override
                public void onResponse(Call<ApplyLoan> call, Response<ApplyLoan> response) {

                }

                @Override
                public void onFailure(Call<ApplyLoan> call, Throwable t) {

                }
            });
        }
        }





    private void displayUserInfo(String message) {
        Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }


}