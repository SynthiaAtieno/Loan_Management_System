package com.example.loanmanagementsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.models.ProfileModel;

import java.util.List;

public class ProfileRecyclerview extends RecyclerView.Adapter<ProfileRecyclerview.MyViewHolder>{

    List<ProfileModel> profileModels;

    public ProfileRecyclerview(List<ProfileModel> profileModels) {
        this.profileModels = profileModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProfileModel profile = profileModels.get(position);
        holder.fullname.setText(profile.getName().toString());
    }

    @Override
    public int getItemCount() {
        return profileModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.fullname);
        }
    }
}
