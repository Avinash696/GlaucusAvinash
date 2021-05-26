package com.personal.glaucusavinash.Interface;

import com.personal.glaucusavinash.ExampleModels.Eexamples;
import com.personal.glaucusavinash.Models.Example;
import com.personal.glaucusavinash.Models.Example2;
import com.personal.glaucusavinash.NewModel.Exampli;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


//    @GET("https://api.openweathermap.org/data/2.5/weather?q=lucknow&appid=e1991533deb53055ab7b41c85f79680d")
//    private getAll();
    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=e1991533deb53055ab7b41c85f79680d

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Example> getMainData(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Example2> getWeatherDesc(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Eexamples> getEexamples(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Exampli> getExampli(@Query("q") String name);
    @GET("?&&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Exampli> getLatitude(@Query("lat") Double lat,@Query("lon") Double lon);
}
