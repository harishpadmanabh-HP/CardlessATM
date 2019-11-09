package com.hp.hp.cardlessatm.Retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {
    // create a method which returns our api interface class
    public Apis getApi()
    {
        //build retrofit object
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://srishti-systems.info/projects/Cardless_ATM/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //connect api class with this builder

        Apis apis=retrofit.create(Apis.class);
        return apis;
    }
}
