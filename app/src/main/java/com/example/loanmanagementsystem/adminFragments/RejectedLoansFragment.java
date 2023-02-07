package com.example.loanmanagementsystem.adminFragments;

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
import com.example.loanmanagementsystem.adapter.RejectedLoansAdapter;
import com.example.loanmanagementsystem.models.RejectedLoans;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RejectedLoansFragment extends Fragment {
    View view;

    RecyclerView recyclerView;
    List<RejectedLoans> rejectedLoans = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RejectedLoansAdapter adapter;
    ProgressBar progressBar;


    public RejectedLoansFragment() {
        // Required empty public constructor
    }

    public static RejectedLoansFragment newInstance(String param1, String param2) {
        RejectedLoansFragment fragment = new RejectedLoansFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRejectedLoans();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rejected_loans2, container, false);
        recyclerView = view.findViewById(R.id.rejected_loans);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RejectedLoansAdapter(rejectedLoans);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        return view;
    }
    public void getRejectedLoans(){
        ApiClient.getApiClient().getRejectedLoans().enqueue(new Callback<List<RejectedLoans>>() {
            @Override
            public void onResponse(Call<List<RejectedLoans>> call, Response<List<RejectedLoans>> response) {
                if (response.isSuccessful() && response.body() != null){
                    rejectedLoans.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<RejectedLoans>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error occurred: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}