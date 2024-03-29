package com.example.loanmanagementsystem.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    TextInputLayout phonetxt, passwordtxt;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        appConfig = new AppConfig(this);

        linearLayout = findViewById(R.id.linear_layout);

        progressDialog = new ProgressDialog(this);
        phonetxt = findViewById(R.id.signin_username);
        passwordtxt = findViewById(R.id.signin_password);
        signin = findViewById(R.id.signIn_btn);
        dont_have_an_account = findViewById(R.id.dont_have_an_account);
        forgot_password = findViewById(R.id.forgot_password);
        alertDialog = new AlertDialog.Builder(this).create();

        if (appConfig.isUserlogin()) {
            String user_id = appConfig.getUserId();
            String username = appConfig.getNameOfUser();
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
            finish();
        }

      /*  if (appConfig.isUserlogin()) {
            String user_id = appConfig.getUserId();
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("user_id", user_id);
            startActivity(intent);
            finish();
        }*/
        dont_have_an_account.setOnClickListener(view -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
            finish();
        });

        signin.setOnClickListener(view -> {
            performSignIn();
            AdminSigIn();
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);

        });
    }

    public void performSignIn() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        String phone, password, user_id;
        phone = phonetxt.getEditText().getText().toString();

        password = passwordtxt.getEditText().getText().toString();

        if (phone.isEmpty()) {
            phonetxt.getEditText().setError("username is required");
            phonetxt.getEditText().requestFocus();
        } else if (password.isEmpty()) {
            passwordtxt.getEditText().setError("password is required");
            passwordtxt.requestFocus();
        } else if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            alertDialog.setTitle("Network Error");
            alertDialog.setMessage("You have no internet connection");
            alertDialog.show();
        }
        else {
            progressDialog.show();
            ApiClient.getApiClient().performUserLogin(phone, password).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("ok")) {
                            /*if (isRememberUserLogin) {*/
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameOfUser(response.body().getName());
                                appConfig.saveUserId(response.body().getUserId());


                            //}
                            //Toast.makeText(SignIn.this, "User_id" + response.body().getUserId(), Toast.LENGTH_LONG).show();
                            Toast.makeText(SignIn.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("username", response.body().getName());
                            startActivity(intent);
                            finish();
                        } else {
                            displayUserInfo(response.body().getMessage());
                            //passwordtxt.getEditText().setText("");
                        }

                    } else {
                        displayUserInfo(response.body().getMessage());
                        //,passwordtxt.getEditText().setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    displayUserInfo(t.getMessage());
                }
            });

        }
    }

    public void AdminSigIn() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        String phone, password;
        phone = phonetxt.getEditText().getText().toString();
        password = passwordtxt.getEditText().getText().toString();


        if (phone.isEmpty() && password.isEmpty()) {
            phonetxt.getEditText().setError("username required");
            phonetxt.getEditText().requestFocus();

            passwordtxt.getEditText().setError("password required");
            passwordtxt.requestFocus();
        } else if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            alertDialog.setTitle("Network Error");
            alertDialog.setMessage("You have no internet connection");
            alertDialog.show();
            //Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();

            ApiClient.getApiClient().performAdminLogin(phone, password).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("admin")) {

                            if (isRememberUserLogin) {
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameOfUser(response.body().getName());
                                appConfig.saveUserId(response.body().getUserId());



                            }
                            Toast.makeText(SignIn.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                            intent.putExtra("username", appConfig.getNameOfUser());
                            startActivity(intent);
                            finish();
                        } else {
                            displayUserInfo(response.body().getMessage());
                            //passwordtxt.getEditText().setText("");
                        }

                    } else {
                        displayUserInfo(response.body().getMessage());
                        //passwordtxt.getEditText().setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    alertDialog.setTitle("Error occurred");
                    alertDialog.setMessage(t.getMessage());
                    alertDialog.show();
                    //displayUserInfo(t.getMessage());

                }
            });
        }
    }

    private void displayUserInfo(String message) {
        Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
        //passwordtxt.getEditText().setText("");
        progressDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

   /* public void checkBoxClicked(View view) {
        isRememberUserLogin = ((CheckBox) view).isChecked();
    }*/

    void saveUserId(String text) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", text);
        editor.apply();
    }

}