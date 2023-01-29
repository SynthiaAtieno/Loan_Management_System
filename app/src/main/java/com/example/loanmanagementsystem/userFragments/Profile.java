package com.example.loanmanagementsystem.userFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.TotalLoans;
import com.example.loanmanagementsystem.activities.MainActivity;
import com.example.loanmanagementsystem.adapter.ProfileRecyclerview;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.activities.SignIn;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {
    TextView fullname, total;
    ApiResponse apiResponse = new ApiResponse();
    AppConfig appConfig;


    /*SignIn signIn = new SignIn();
    ProfileRecyclerview adapter;*/

    LinearLayoutManager linearLayoutManager;
    View view;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_profile, container, false);
         fullname = view.findViewById(R.id.fullname);
         total = view.findViewById(R.id.totalLoan);
        return  view;    }

    public void getProfile(){

        ApiClient.getApiClient().getProfile(appConfig.getUserId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    //appConfig.updateUserLoginStatus(true);
                    //appConfig.saveUserId(appConfig.getUserId());
                    fullname.setText(appConfig.getNameOfUser());
                    //Toast.makeText(getContext(), "user_id"+apiResponse.getUserId(), Toast.LENGTH_SHORT).show();
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
                    //appConfig.updateUserLoginStatus(true);
                    total.setText(response.body().getTotal());
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