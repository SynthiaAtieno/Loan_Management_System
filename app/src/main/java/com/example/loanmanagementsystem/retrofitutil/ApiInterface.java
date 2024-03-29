package com.example.loanmanagementsystem.retrofitutil;

import com.example.loanmanagementsystem.models.Approve;
import com.example.loanmanagementsystem.models.TotalLoans;
import com.example.loanmanagementsystem.models.ApiResponse;
import com.example.loanmanagementsystem.models.ApplyLoan;
import com.example.loanmanagementsystem.models.ApprovedLoans;
import com.example.loanmanagementsystem.models.InProgressLoans;
import com.example.loanmanagementsystem.models.Loan;
import com.example.loanmanagementsystem.models.RejectedLoans;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<ApiResponse> performUserSignIn(@Field("phone") String phone, @Field("fullname")String fullname, @Field("email") String Password, @Field("password") String email);

    @FormUrlEncoded
    //api endpoint
    @POST("login.php")
    Call<ApiResponse> performUserLogin(@Field("phone")String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("admin.php")
    Call<ApiResponse> performAdminLogin(@Field("username") String username, @Field("password") String password);


    @GET("totalLoan.php")
    Call<TotalLoans> fetchLoan(@Query("user_id") String user_id);

    @GET("approvedLoanForSingleUser.php")
    Call<TotalLoans> ApprovedLoans(@Query("user_id") String user_id);

    @GET("rejectedLoansFor SingleUser.php")
    Call<TotalLoans> RejectedLoans(@Query("user_id") String user_id);

    @GET("pendingLoans.php")
    Call<TotalLoans> PendingLoans(@Query("user_id") String user_id);

    @GET("getappliedloans.php")
    Call<List<Loan>> getLoan();

    @GET("loanInProgress.php")
    Call<List<InProgressLoans>> getInProgressLoan();

    @GET("approveLoan.php")
    Call<Approve> approveLoan(@Query("loan_id") String loan_id);

    @GET("rejectLoan.php")
    Call<Approve> rejectLoan(@Query("loan_id") String loan_id);

    @GET("approvedLoans.php")
    Call<List<ApprovedLoans>> getApprovedLoan();

    @GET("rejectedLoans.php")
    Call<List<RejectedLoans>> getRejectedLoans();

    @PATCH("approveLoan.php/{loan_id}")
    Call<ApiResponse> approveLoan(@Path("loan_id") int loan_id);

    @GET("getProfile.php")
    Call<ApiResponse> getProfile(@Query("user_id") String id);

    @FormUrlEncoded
    @POST("apply_loan.php")
    Call<ApplyLoan> applyLoan(@Field("amount") String amount, @Field("description") String description, @Field("user_id") String user_id, @Field("status_id") int status_id );



}
