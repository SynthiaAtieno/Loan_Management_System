package com.example.loanmanagementsystem.userFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.models.TotalLoans;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {
    TextView fullname, total, overdueLoans;
    ApiResponse apiResponse = new ApiResponse();
    AppConfig appConfig;
    ProgressBar progressBar;


    /*SignIn signIn = new SignIn();
    ProfileRecyclerview adapter;*/

    LinearLayoutManager linearLayoutManager;
    View view;
    Locale locale = new Locale("en", "ke");
    NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(locale);

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appConfig = new AppConfig(getContext());
        if (appConfig.isUserlogin()) {
            String user_id = appConfig.getUserId();
            String username = appConfig.getNameOfUser();
        }
        getProfile();
        getTotal();
        totalApprovedLoan();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_profile, container, false);
         fullname = view.findViewById(R.id.fullname);
         total = view.findViewById(R.id.totalLoan);
         overdueLoans = view.findViewById(R.id.overdue_loans);
         progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        return  view;    }

    public void getProfile(){

        ApiClient.getApiClient().getProfile(appConfig.getUserId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    fullname.setText(appConfig.getNameOfUser());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error Occurred "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  void getTotal(){
        ApiClient.getApiClient().fetchLoan(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful() && response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    appConfig.updateUserLoginStatus(true);
                    total.setText(defaultFormat.format(response.body().getTotal()));
                }
                else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TotalLoans> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void totalApprovedLoan(){
        ApiClient.getApiClient().ApprovedLoans(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful() && response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    overdueLoans.setText(defaultFormat.format(response.body().getTotal()));
                }
                else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TotalLoans> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}