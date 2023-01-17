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
    TextInputLayout nametxt,usernametxt,emailtxt, passwordtxt;
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
        nametxt = findViewById(R.id.signup_name);
        usernametxt = findViewById(R.id.signup_username);
        emailtxt = findViewById(R.id.signup_email);
        passwordtxt = findViewById(R.id.signup_password);
        linearLayout = findViewById(R.id.linearlayout);

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
                //progressDialog.show();
            }
        });

    }

   /* public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(SignUp.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;*/
    //}
    private  void  performSignUp(){
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        String username, password, name, email;
        username = usernametxt.getEditText().getText().toString();
        password = passwordtxt.getEditText().getText().toString();
        name = nametxt.getEditText().getText().toString();
        email = emailtxt.getEditText().getText().toString();
        if (username.isEmpty() && password.isEmpty() && name.isEmpty() && email.isEmpty()){
            usernametxt.getEditText().setError("username required");
            usernametxt.getEditText().requestFocus();
            passwordtxt.getEditText().setError("password required");
            passwordtxt.getEditText().requestFocus();
            nametxt.getEditText().setError("name required");
            nametxt.getEditText().requestFocus();
            emailtxt.getEditText().setError("email required");
            emailtxt.getEditText().requestFocus();
            displayUserInfo("Please fill all the fields");

        }
        else if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            alertDialog.setTitle("Network Error");
            alertDialog.setMessage("You have no internet connection");
            alertDialog.show();
            //Toast.makeText(SignUp.this, "No Internet connection!", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.show();
            ApiClient.getApiClient().performUserSignIn(username,name, email, password).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){

                        if (response.body().getStatus().equals("ok"))
                        {
                            Toast.makeText(SignUp.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            startActivity(new Intent(SignUp.this, SignIn.class));
                            finish();

                        }else {
                            displayUserInfo(response.body().getMessage());
                            passwordtxt.getEditText().setText("");
                        }
                    }else {
                        displayUserInfo(response.body().getMessage());
                        passwordtxt.getEditText().setText("");
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
         passwordtxt.getEditText().setText("");
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}