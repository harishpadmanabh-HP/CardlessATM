package com.hp.hp.cardlessatm.Retro;

import com.hp.hp.cardlessatm.Model.HistoryModel;
import com.hp.hp.cardlessatm.Model.LoginModel;
import com.hp.hp.cardlessatm.Model.OtpModel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apis {

    @FormUrlEncoded
    @POST("login")
   Call<LoginModel> LOGIN_MODEL_CALL(@Field("email") String email,
                                     @Field("num") String num);

    @FormUrlEncoded
    @POST("push_notification")
    Call<OtpModel> OTP_MODEL_CALL(@Field("userid") String uid);

    @FormUrlEncoded
    @POST("transaction_history")
    Call<HistoryModel> HISTORY_MODEL_CALL(@Field("userid") String uid);

}
