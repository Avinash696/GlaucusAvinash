package com.personal.glaucusavinash.Singleton;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
   static String url="https://api.openweathermap.org/data/2.5/weather/";
    //var
    public static Retrofit retrofit=null;

//fn-not void
   public static Retrofit getClient(){
     if(retrofit==null){
         retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
     }
     return retrofit;
    }
}
