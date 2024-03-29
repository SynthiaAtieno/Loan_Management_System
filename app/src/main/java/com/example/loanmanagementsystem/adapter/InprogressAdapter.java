package com.example.loanmanagementsystem.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanmanagementsystem.ApproveLoan;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.models.InProgressLoans;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InprogressAdapter extends RecyclerView.Adapter<InprogressAdapter.MyViewHolder> {
    private List<InProgressLoans> inProgressLoansList;

    public InprogressAdapter(List<InProgressLoans> inProgressLoansList) {
        this.inProgressLoansList = inProgressLoansList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_prgress_loan_list, parent, false);
        return new InprogressAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        InProgressLoans loans = inProgressLoansList.get(position);
        if (loans.getStatus().equals("In progress")){
            holder.status.setBackgroundColor(Color.YELLOW);
        }
        if (loans.getStatus().equals("Approved")){
            holder.status.setBackgroundColor(Color.GREEN);
        }

        if (loans.getStatus().equals("Rejected")){
            holder.status.setBackgroundColor(Color.RED);
        }
        Locale locale = new Locale("en", "ke");
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(locale);
       // holder.user_id.setText(loans.getUserId());
        holder.description.setText(loans.getDescription());
        holder.amount.setText(defaultFormat.format(loans.getAmount()));
        holder.status.setText(loans.getStatus());
        holder. name.setText(loans.getName());
        holder.loan_id.setText(loans.getLoanId());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loans.setStatus("Approved");
            }
        });
        holder.mainCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApproveLoan.class);
                intent.putExtra("name",loans.getName());
                intent.putExtra("amount",defaultFormat.format(loans.getAmount()));
                intent.putExtra("description",loans.getDescription());
                intent.putExtra("status",loans.getStatus());
                intent.putExtra("loanId", loans.getLoanId());
                view.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return inProgressLoansList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView amount, description, status, name, user_id, loan_id;
        Button approve, reject;
        CardView mainCards;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.txt_amount);
            description = itemView.findViewById(R.id.txt_description);
            status = itemView.findViewById(R.id.txt_status);
            name = itemView.findViewById(R.id.txt_name);
            //user_id = itemView.findViewById(R.id.loan_id);
            loan_id = itemView.findViewById(R.id.txt_loan_id);
            mainCards = itemView.findViewById(R.id.mainCard);


            approve = itemView.findViewById(R.id.approve);
            reject = itemView.findViewById(R.id.reject);

        }
    }
}
