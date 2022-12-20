package com.example.loanmanagementsystem.apputil;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loanmanagementsystem.R;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.ProfileModel;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;
    private ApiResponse apiResponse = new ApiResponse();
    //ProfileModel profileModel = new ProfileModel();

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), context.MODE_PRIVATE);

    }

    public void updateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login), status);
        editor.apply();
    }
    public  boolean isUserlogin(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login), false);

    }

    public void saveNameOfUser(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user), name);
        editor.apply();

    }


    public void saveUserId(String user_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_id), user_id);
        editor.apply();


    }
    public String getNameOfUser(){
        return  sharedPreferences.getString(context.getString(R.string.pref_name_of_user), "User");
    }
    public String getUserId(){
        return  sharedPreferences.getString(context.getString(R.string.pref_user_id), "User");
    }
}
