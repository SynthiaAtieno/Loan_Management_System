package com.example.loanmanagementsystem.retrofitutil;

import com.example.loanmanagementsystem.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<ApiResponse> performUserSignIn(@Field("username") String username, @Field("name")String nane, @Field("password") String Password, @Field("email") String email);

    @FormUrlEncoded
    //api endpoint
    @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("admin.php")
    Call<ApiResponse> performAdminLogin(@Field("username") String username, @Field("password") String password);

}
