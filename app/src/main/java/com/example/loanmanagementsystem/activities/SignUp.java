package com.example.loanmanagementsystem.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.OTP;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;
import com.example.loanmanagementsystem.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    TextInputLayout fullnametxt,phonetxt,emailtxt, passwordtxt, conpass;
    Button signup;
    TextView already_have_an_account;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        fullnametxt = findViewById(R.id.signup_full_name);
        phonetxt = findViewById(R.id.signup_phone);
        emailtxt = findViewById(R.id.signup_email);
        passwordtxt = findViewById(R.id.signup_password);
        linearLayout = findViewById(R.id.linearlayout);
        conpass = findViewById(R.id.signup_conPassword);

        signup = findViewById(R.id.signup_btn);
        already_have_an_account = findViewById(R.id.already_have_an_account);

        alertDialog = new AlertDialog.Builder(this).create();

        already_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignIn.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();
                progressDialog.setTitle("Processing...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
            }
        });

    }

    private  void  performSignUp(){
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        String fullname, password, phone, email, confirmPassword;
        phone = phonetxt.getEditText().getText().toString();
        password = passwordtxt.getEditText().getText().toString();
        fullname = fullnametxt.getEditText().getText().toString();
        email = emailtxt.getEditText().getText().toString();
        confirmPassword = conpass.getEditText().getText().toString();
        if (fullname.isEmpty()){
            fullnametxt.getEditText().setError("full name is required");
            fullnametxt.getEditText().requestFocus();
        }

        else if (phone.isEmpty()){
            phonetxt.getEditText().setError("phone is required");
            phonetxt.getEditText().requestFocus();

        }
        else if (phone.length()<9){
            phonetxt.getEditText().setError("Please input a valid phone number");
            phonetxt.requestFocus();
        }
        else if (email.isEmpty()){
            emailtxt.getEditText().setError("email required");
            emailtxt.getEditText().requestFocus();
        }
        else if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            emailtxt.getEditText().setError("Please enter a valid email");
            emailtxt.requestFocus();
        }
        else if (password.isEmpty()){
            passwordtxt.getEditText().setError("password required");
            passwordtxt.getEditText().requestFocus();
        }
        else if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            conpass.requestFocus();
        }

        else if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            alertDialog.setTitle("Network Error");
            alertDialog.setMessage("You have no internet connection");
            alertDialog.show();
            //Toast.makeText(SignUp.this, "No Internet connection!", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.show();

            ApiClient.getApiClient().performUserSignIn(phone,fullname, email, password).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){

                        if (response.body().getStatus().equals("ok"))
                        {
                            Toast.makeText(SignUp.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            startActivity(new Intent(SignUp.this, SignIn.class));
                            //startActivity(new Intent(SignUp.this, SignIn.class));
                            finish();

                        }else {
                            displayUserInfo(response.body().getMessage());
                            //passwordtxt.getEditText().setText("");
                        }
                    }else {
                        displayUserInfo(response.body().getMessage());
                        //passwordtxt.getEditText().setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    alertDialog.setTitle("Error occurred");
                    alertDialog.setMessage(t.getMessage());
                    alertDialog.show();
                }
            });

        }
    }
    private void displayUserInfo(String message){
        Snackbar.make(linearLayout,message,Snackbar.LENGTH_LONG).show();
         //passwordtxt.getEditText().setText("");
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}