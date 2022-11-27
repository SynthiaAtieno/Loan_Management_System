package com.example.loanmanagementsystem.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loanmanagementsystem.R;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), context.MODE_PRIVATE);

    }

    public  boolean isUserlogin(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login), false);

    }

    public void updateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login), status);
        editor.apply();
    }

    public void saveNameOfUser(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user), name);
        editor.apply();

    }

    public String getNameOfUser(){
        return  sharedPreferences.getString(context.getString(R.string.pref_name_of_user), "Unknown");
    }
}
