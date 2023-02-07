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


    View view;

    public All_Loans_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all__loans_, container, false);

        return view;
    }




}