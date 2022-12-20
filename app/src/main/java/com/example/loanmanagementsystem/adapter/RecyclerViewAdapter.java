package com.example.loanmanagementsystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanmanagementsystem.models.Loan;
import com.example.loanmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{


    List<Loan> loanList;

    public RecyclerViewAdapter(List<Loan> loans) {

        this.loanList = loans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_loan_list, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Loan loan = loanList.get(position);

        if (loan.getStatus().equals("In progress")){
            holder.status.setBackgroundColor(Color.YELLOW);
        }
        if (loan.getStatus().equals("Approved")){
            holder.status.setBackgroundColor(Color.GREEN);
        }

        if (loan.getStatus().equals("Rejected")){
            holder.status.setBackgroundColor(Color.RED);
        }
        holder.user_id.setText(loan.getUserId());
        holder.description.setText(loan.getDescription());
        holder.amount.setText(loan.getAmount());
        holder.status.setText(loan.getStatus());
        holder. name.setText(loan.getName());
        holder.loan_id.setText(loan.getLoanId());


    }

    @Override
    public int getItemCount() {
        return loanList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView amount, description, status, name, user_id, loan_id;
        Button approve, reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            approve = itemView.findViewById(R.id.approve);
            reject = itemView.findViewById(R.id.reject);
            user_id = itemView.findViewById(R.id.txt_userId);
            amount = itemView.findViewById(R.id.txt_amount);
            description = itemView.findViewById(R.id.txt_description);
            status = itemView.findViewById(R.id.txt_status);
            name = itemView.findViewById(R.id.txt_name);
            loan_id = itemView.findViewById(R.id.loan_id);

        }
    }
}
