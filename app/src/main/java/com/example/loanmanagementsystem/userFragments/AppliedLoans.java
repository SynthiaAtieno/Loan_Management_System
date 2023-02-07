package com.example.loanmanagementsystem.userFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.ApplyLoanActivity;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.models.TotalLoans;
import com.example.loanmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.Loan;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedLoans extends Fragment {

    TextView txtname, totalLoans, approvedLoans, pendingLoans, rejectedLoans;
    CardView approved, rejected, pending;
    TextView applyLoan;
    ProgressBar progressBar;

    View view;
    ArrayList<Loan> loanArrayList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ApplyLoanFragment applyLoanFragment;

    ProgressDialog progressDialog;
    AppConfig appConfig;
    AlertDialog alertDialog;
    Locale locale = new Locale("en", "ke");
    NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(locale);

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
        totalApprovedLoan();
        totalPendingLoan();
        totalRejectedLoans();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loans, container, false);
        txtname = view.findViewById(R.id.name);
        applyLoan = view.findViewById(R.id.applyLoanbtn);
        totalLoans = view.findViewById(R.id.totalLoans);
        applyLoanFragment = new ApplyLoanFragment();
        pendingLoans = view.findViewById(R.id.totalLoansPending);
        approvedLoans = view.findViewById(R.id.totalApprovedLoans);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog = new ProgressDialog(getContext());
        appConfig = new AppConfig(getContext());
        rejectedLoans = view.findViewById(R.id.totalLoansRejected);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);



        applyLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ApplyLoanActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
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

                        totalLoans.setText(defaultFormat.format(response.body().getTotal()));
                    } else {
                        //progressDialog.dismiss();
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TotalLoans> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void totalApprovedLoan(){
        ApiClient.getApiClient().ApprovedLoans(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful() && response.body() != null){
                    approvedLoans.setText(defaultFormat.format(response.body().getTotal()));
                }
                else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TotalLoans> call, Throwable t) {
                alertDialog.setTitle("Failed");
                alertDialog.setMessage(t.getMessage());
                alertDialog.show();
                Toast.makeText(getContext(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void totalPendingLoan(){
        ApiClient.getApiClient().PendingLoans(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful() && response.body() != null){
                    pendingLoans.setText(defaultFormat.format(response.body().getTotal()));
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


    private void displayUserInfo(String message) {
       /* Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG).show();
        // passwordtxt.getEditText().setText("");
        progressDialog.dismiss();*/

    }
    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout,fragment);
        fragmentTransaction.commit();
    }
    private void totalRejectedLoans(){
        ApiClient.getApiClient().RejectedLoans(appConfig.getUserId()).enqueue(new Callback<TotalLoans>() {
            @Override
            public void onResponse(Call<TotalLoans> call, Response<TotalLoans> response) {
                if (response.isSuccessful() && response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    rejectedLoans.setText(defaultFormat.format(response.body().getTotal()));
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