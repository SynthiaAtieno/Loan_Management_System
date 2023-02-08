package com.example.loanmanagementsystem.adminFragments;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class All_Loans_Fragment extends Fragment {


    LineChart  lineChart;
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

        lineChart = view.findViewById(R.id.line_chart);

        LineDataSet lineDataSet = new LineDataSet(datavalues(),"Data set1");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData data = new LineData(iLineDataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        return view;
    }
    private  ArrayList<Entry> datavalues(){
        ArrayList<Entry> datavals = new ArrayList<>();
        datavals.add(new Entry(0,15));
        datavals.add(new Entry(2,25));
        datavals.add(new Entry(3,5));
        datavals.add(new Entry(4,3));
        datavals.add(new Entry(5,28));
        datavals.add(new Entry(6,9));

        return datavals;
    }


}