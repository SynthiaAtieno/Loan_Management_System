package com.example.loanmanagementsystem.userFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.adapter.ProfileRecyclerview;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.activities.SignIn;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {
    TextView fullname;
    ApiResponse apiResponse = new ApiResponse();


    SignIn signIn = new SignIn();
    ProfileRecyclerview adapter;

    LinearLayoutManager linearLayoutManager;
    View view;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProfile();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.fragment_profile, container, false);
         fullname = view.findViewById(R.id.fullname);
        return  view;    }

    public void getProfile(){
        SignIn signIn = new SignIn();

        //signIn.performSignIn();

        ApiClient.getApiClient().getProfile("36").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    fullname.setText(response.body().getName());
                    //Toast.makeText(getContext(), "user_id"+apiResponse.getUserId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error Occurred "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}