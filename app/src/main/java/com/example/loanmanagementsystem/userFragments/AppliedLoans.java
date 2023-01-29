package com.example.loanmanagementsystem.userFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.TotalLoans;
import com.example.loanmanagementsystem.activities.Admin;
import com.example.loanmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.Loan;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;
import com.example.loanmanagementsystem.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedLoans extends Fragment {

    TextView txtname, totalLoans, approvedLoans, pendingLoans;
    CardView approved, rejected, pending;

    View view;
    ArrayList<Loan> loanArrayList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;
    AppConfig appConfig;

    //View view;

    public AppliedLoans() {
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
        fetchLoans();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loans, container, false);
        txtname = view.findViewById(R.id.name);
        totalLoans = view.findViewById(R.id.totalLoans);
        pendingLoans = view.findViewById(R.id.pending_loans);
        approvedLoans = view.findViewById(R.id.approved_loans);

        appConfig = new AppConfig(getContext());
        return view;
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtname = view.findViewById(R.id.name);
        totalLoans = view.findViewById(R.id.totalLoans);
        pendingLoans = view.findViewById(R.id.pending_loans);
        approvedLoans = view.findViewById(R.id.approved_loans);

        appConfig = new AppConfig(getContext());
        getProfile();
        fetchLoans();
    }*/
    public void getProfile() {

        ApiClient.getApiClient().getProfile(appConfig.getUserId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //appConfig.updateUserLoginStatus(true);
                    txtname.setText(appConfig.getNameOfUser());
                    //Toast.makeText(getContext(), "user_id"+apiResponse.getUserId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error Occurred " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLoans() {
        ApiClient.getApiClient().fetchLoan(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getAppliedLoans();
                        totalLoans.setText(response.body().getTotal());
                    } else {
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TotalLoans> call, Throwable t) {
                Toast.makeText(getContext(), "Error Occurred" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAppliedLoans() {
        progressDialog.setTitle("Getting Loans");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


    }

    private void displayUserInfo(String message) {
       /* Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG).show();
        // passwordtxt.getEditText().setText("");
        progressDialog.dismiss();*/

    }
}