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

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.RejectedLoansActivity;
import com.example.loanmanagementsystem.models.RejectedLoans;

import java.util.List;

public class RejectedLoansAdapter extends RecyclerView.Adapter<RejectedLoansAdapter.MyViewHolder>{

    List<RejectedLoans> rejectedLoans;

    public RejectedLoansAdapter(List<RejectedLoans> rejectedLoans) {
        this.rejectedLoans = rejectedLoans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_loan_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RejectedLoans loans = rejectedLoans.get(position);

        if (loans.getStatus().equals("Rejected")){
            holder.status.setBackgroundColor(Color.RED);
        }

        holder.user_id.setText(loans.getUserId());
        holder.description.setText(loans.getDescription());
        holder.amount.setText("Ksh."+loans.getAmount()+".00");
        holder.status.setText(loans.getStatus());
        holder. name.setText(loans.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RejectedLoansActivity.class);
                intent.putExtra("name",loans.getName());
                intent.putExtra("amount","Ksh."+loans.getAmount()+".00");
                intent.putExtra("description",loans.getDescription());
                intent.putExtra("status",loans.getStatus());
                intent.putExtra("loan_id", loans.getLoan_id());
                intent.putExtra("date", loans.getDate());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rejectedLoans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView amount, description, status, name, user_id, date;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.txt_amount);
            description = itemView.findViewById(R.id.txt_description);
            status = itemView.findViewById(R.id.txt_status);
            name = itemView.findViewById(R.id.txt_name);
            user_id = itemView.findViewById(R.id.loan_id);
            cardView = itemView.findViewById(R.id.adminLoanList);
        }
    }
}
