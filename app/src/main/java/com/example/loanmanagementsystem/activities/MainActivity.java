package com.example.loanmanagementsystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementsystem.userFragments.ApplyLoanFragment;
import com.example.loanmanagementsystem.userFragments.Profile;
import com.example.loanmanagementsystem.userFragments.AppliedLoans;
import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.apputil.AppConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    TextView logouttxt, txtmain;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;
    ApplyLoanFragment applyLoanFragment = new ApplyLoanFragment();
    AppliedLoans loansFragment = new AppliedLoans();
    private Button logout;
    AppConfig appConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // logout = findViewById(R.id.logout);

        appConfig = new AppConfig(this);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, loansFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        replaceFragment(new AppliedLoans());
                        return true;

                    case R.id.nav_profile:
                        replaceFragment(new Profile());
                        return true;

                        case R.id.apply_loan:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, applyLoanFragment).commit();
                            return true;

                        case R.id.nav_logout:
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Do you want to quit?");
                            builder.setTitle("Alert");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) ->{
                                appConfig.updateUserLoginStatus(false);
                                Toast.makeText(MainActivity.this, "Logged out successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SignIn.class));
                                finish();
                            });
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) ->
                            {
                               dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            return  true;
                }
                return true;
            }
        });
   }

   private void  replaceFragment(Fragment fragment){
       FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.fragment_layout,fragment);
       fragmentTransaction.commit();
   }


}