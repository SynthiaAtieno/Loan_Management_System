package com.example.loanmanagementsystem.adminFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.adapter.InprogressAdapter;
import com.example.loanmanagementsystem.models.InProgressLoans;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InProrgressLoan extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<InProgressLoans> inProgressLoansList = new ArrayList<>();
    //List<Loan> loanList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    InprogressAdapter adapter;


    public InProrgressLoan() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InProrgressLoan newInstance(String param1, String param2) {
        InProrgressLoan fragment = new InProrgressLoan();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInProgressLoans();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_in_prorgress_loan, container, false);
        recyclerView = view.findViewById(R.id.inprogress_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new InprogressAdapter(inProgressLoansList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getInProgressLoans() {
        ApiClient.getApiClient().getInProgressLoan().enqueue(new Callback<List<InProgressLoans>>() {
            @Override
            public void onResponse(Call<List<InProgressLoans>> call, Response<List<InProgressLoans>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inProgressLoansList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<InProgressLoans>> call, Throwable t) {
                Toast.makeText(getContext(), "Error occurred: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}