package com.hp.hp.cardlessatm.Retro;

import com.hp.hp.cardlessatm.Model.LoginModel;


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


}
