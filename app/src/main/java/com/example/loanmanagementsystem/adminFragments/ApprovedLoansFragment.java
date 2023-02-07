package com.example.loanmanagementsystem.adminFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.adapter.ApprovedLoanAdapter;
import com.example.loanmanagementsystem.models.ApprovedLoans;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApprovedLoansFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<ApprovedLoans> approvedLoansList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ApprovedLoanAdapter adapter;

    ProgressBar progressBar;
    public ApprovedLoansFragment() {
        // Required empty public constructor
    }

    public static ApprovedLoansFragment newInstance(String param1, String param2) {
        ApprovedLoansFragment fragment = new ApprovedLoansFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApprovedLoans();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_approved_loans, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ApprovedLoanAdapter(approvedLoansList);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        return view;
    }
    public  void getApprovedLoans(){

        ApiClient.getApiClient().getApprovedLoan().enqueue(new Callback<List<ApprovedLoans>>() {

            @Override
            public void onResponse(Call<List<ApprovedLoans>> call, Response<List<ApprovedLoans>> response) {
                if (response.isSuccessful() && response.body() != null){
                    approvedLoansList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);


                }else {
                    Toast.makeText(getContext(), "Response not succeseful", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<List<ApprovedLoans>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error occurred: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}