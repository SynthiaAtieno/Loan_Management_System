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
import com.example.loanmanagementsystem.activities.Admin;
import com.example.loanmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.loanmanagementsystem.apputil.AppConfig;
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

    TextView txtname;
    CardView approved, rejected, pending;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loans, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtname = view.findViewById(R.id.txt_name);
        String username = getActivity().getIntent().getStringExtra("username");
        txtname.setText(username);
        recyclerView = view.findViewById(R.id.recyclerview);

        appConfig = new AppConfig(getContext());

        fetchLoans();
    }

    private void fetchLoans() {

        String userid = getActivity().getIntent().getStringExtra("user_id");
        //String username = appConfig.getNameOfUser();

        ApiClient.getApiClient2().fetchLoans(userid).enqueue(new Callback<List<Loan>>() {
            @Override
            public void onResponse(Call<List<Loan>> call, Response<List<Loan>> response) {
                if (response.isSuccessful() && response.body() != null){
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerViewAdapter = new RecyclerViewAdapter(loanArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    loanArrayList.addAll(response.body());
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                else {
                    displayUserInfo("Data not found");

                }
            }

            @Override
            public void onFailure(Call<List<Loan>> call, Throwable t) {
                Toast.makeText(getContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
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