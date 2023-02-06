package com.example.loanmanagementsystem.adminFragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.loanmanagementsystem.models.Loan;
import com.example.loanmanagementsystem.retrofitutil.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class All_Loans_Fragment extends Fragment {

    RecyclerView recyclerView;
    CardView approved, rejected, pending;
    List<Loan> loanList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter adapter;
    ApprovedLoansFragment approvedLoansFragment = new ApprovedLoansFragment();
    RejectedLoansFragment rejectedLoansFragment = new RejectedLoansFragment();
    InProrgressLoan inProrgressLoanFragment = new InProrgressLoan();
    View view;

    public All_Loans_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoans();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all__loans_, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(loanList);
        recyclerView.setAdapter(adapter);

        approved = view.findViewById(R.id.approved_loans);
        rejected = view.findViewById(R.id.rejected_loans);
        pending = view.findViewById(R.id.pending_loans);

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_layout, approvedLoansFragment).commit();

            }
        });

        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_layout, rejectedLoansFragment).commit();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_layout, inProrgressLoanFragment).commit();
            }
        });


        return view;
    }

    private void getLoans() {
        ApiClient.getApiClient().getLoan().enqueue(new Callback<List<Loan>>() {
            @Override
            public void onResponse(Call<List<Loan>> call, Response<List<Loan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loanList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Loan>> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}