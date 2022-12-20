package com.example.loanmanagementsystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.loanmanagementsystem.adminFragments.ApprovedLoansFragment;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.example.loanmanagementsystem.adminFragments.All_Loans_Fragment;
import com.example.loanmanagementsystem.adminFragments.InProrgressLoan;
import com.example.loanmanagementsystem.adminFragments.RejectedLoansFragment;
import com.example.loanmanagementsystem.models.InProgressLoans;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Admin extends AppCompatActivity {

    All_Loans_Fragment loansFragment = new All_Loans_Fragment();

    InProgressLoans progressLoans = new InProgressLoans();
    BottomNavigationView bottomNavigationView;

    AppConfig appConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        appConfig = new AppConfig(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_layout, loansFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.admin_all_loans:
                        replaceFragment(new All_Loans_Fragment());
                        return true;

                    case R.id.admin_loan_approved:
                        replaceFragment(new ApprovedLoansFragment());
                        return true;

                    case R.id.admin_loan_rejected:
                        replaceFragment(new RejectedLoansFragment());
                        return true;


                    case R.id.admin_loan_inprogress:
                        replaceFragment(new InProrgressLoan());
                        return true;

                    case R.id.nav_logout:
                        appConfig.updateUserLoginStatus(false);
                        startActivity(new Intent(Admin.this, SignIn.class));
                        finish();
                        return  true;
                }
                return true;
            }
        });




    }

    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_layout,fragment);
        fragmentTransaction.commit();
    }

  /*  private void getLoans() {
        ApiClient.getApiClient().getLoan().enqueue(new Callback<List<Loan>>() {
            @Override
            public void onResponse(Call<List<Loan>> call, Response<List<Loan>> response) {
                if (response.isSuccessful() && response.body() != null){
                    loanList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Loan>> call, Throwable t) {
                Toast.makeText(Admin.this, "Error"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}