package com.example.loanmanagementsystem.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;
import com.example.loanmanagementsystem.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    TextInputLayout usernametxt, passwordtxt;
    Button signin;
    TextView dont_have_an_account, forgot_password;
    private boolean isRememberUserLogin = false;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    AppConfig appConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        appConfig = new AppConfig(this);

        linearLayout = findViewById(R.id.linear_layout);

        progressDialog = new ProgressDialog(this);
        usernametxt = findViewById(R.id.signin_username);
        passwordtxt = findViewById(R.id.signin_password);
        signin = findViewById(R.id.signIn_btn);
        dont_have_an_account = findViewById(R.id.dont_have_an_account);
        forgot_password = findViewById(R.id.forgot_password);
        alertDialog = new AlertDialog.Builder(this).create();

        AdminSigIn();
        performSignIn();
        dont_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignIn();
                AdminSigIn();
                progressDialog.setTitle("Processing...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);

            }
        });
    }

    private void performSignIn() {

        if (appConfig.isUserlogin()){
            String username = appConfig.getNameOfUser();
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }
        String username, password;
        username = usernametxt.getEditText().getText().toString();
        password = passwordtxt.getEditText().getText().toString();

        if (username.isEmpty() && password.isEmpty()) {
            usernametxt.getEditText().setError("username required");
            usernametxt.getEditText().requestFocus();

            passwordtxt.getEditText().setError("password required");
            passwordtxt.requestFocus();
        } else {
            progressDialog.show();
            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(username, password);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("ok")) {
                            if (isRememberUserLogin){
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameOfUser(username);
                            }
                            Toast.makeText(SignIn.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            //startActivity(new Intent(SignIn.this, MainActivity.class));
                            finish();
                        } else {
                            displayUserInfo(response.body().getMessage());
                            passwordtxt.getEditText().setText("");
                        }

                    } else {
                        displayUserInfo(response.body().getMessage());
                        passwordtxt.getEditText().setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });
        }


    }

    private void AdminSigIn() {
        if (appConfig.isUserlogin()){
            String username = appConfig.getNameOfUser();
            Intent intent = new Intent(SignIn.this, Admin.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }
        String username, password;
        username = usernametxt.getEditText().getText().toString();
        password = passwordtxt.getEditText().getText().toString();

        if (username.isEmpty() && password.isEmpty()) {
            usernametxt.getEditText().setError("username required");
            usernametxt.getEditText().requestFocus();

            passwordtxt.getEditText().setError("password required");
            passwordtxt.requestFocus();
        } else {
            progressDialog.show();
            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performAdminLogin(username, password);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("admin")) {

                            if (isRememberUserLogin){
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameOfUser(username);
                            }
                            Toast.makeText(SignIn.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            //startActivity(new Intent(SignIn.this, MainActivity.class));
                            finish();
                        } else {
                            displayUserInfo(response.body().getMessage());
                            passwordtxt.getEditText().setText("");
                        }

                    } else {
                        displayUserInfo(response.body().getMessage());
                        passwordtxt.getEditText().setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });
        }


    }


    private void displayUserInfo(String message) {
        Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
        passwordtxt.getEditText().setText("");
        progressDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void checkBoxClicked(View view){
        isRememberUserLogin = ((CheckBox)view).isChecked();
    }


}