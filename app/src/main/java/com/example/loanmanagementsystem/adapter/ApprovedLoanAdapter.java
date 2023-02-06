package com.example.loanmanagementsystem.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanmanagementsystem.ApprovedLoansActivity;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.RejectedLoansActivity;
import com.example.loanmanagementsystem.models.ApprovedLoans;

import java.util.List;

public class ApprovedLoanAdapter extends RecyclerView.Adapter<ApprovedLoanAdapter.MyViewHolder>{

    List<ApprovedLoans> approvedLoansList;
   // List<RejectedLoans> rejectedLoans;

    public ApprovedLoanAdapter(List<ApprovedLoans> approvedLoansList) {
        this.approvedLoansList = approvedLoansList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_loan_list, parent, false);
        return new ApprovedLoanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ApprovedLoans loans = approvedLoansList.get(position);

        if (loans.getStatus().equals("Approved")){
            holder.status.setBackgroundColor(Color.GREEN);
        }

        holder.loan_id.setText(loans.getLoanId());
        holder.description.setText(loans.getDescription());
        holder.amount.setText("Ksh."+loans.getAmount()+".00");
        holder.status.setText(loans.getStatus());
        holder. name.setText(loans.getName());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApprovedLoansActivity.class);
                intent.putExtra("name", loans.getName());
                intent.putExtra("amount", "Ksh."+loans.getAmount()+".00");
                intent.putExtra("status", loans.getStatus());
                intent.putExtra("description", loans.getDescription());
                intent.putExtra("loanId", loans.getLoanId());
                intent.putExtra("date", loans.getDate());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return approvedLoansList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView amount, description, status, name, loan_id;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.txt_amount);
            description = itemView.findViewById(R.id.txt_description);
            status = itemView.findViewById(R.id.txt_status);
            name = itemView.findViewById(R.id.txt_name);
            loan_id = itemView.findViewById(R.id.loan_id);

            cardView = itemView.findViewById(R.id.adminLoanList);
        }
    }
}
