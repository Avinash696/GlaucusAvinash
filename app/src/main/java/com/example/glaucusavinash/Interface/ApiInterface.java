package com.example.glaucusavinash.Interface;

import com.example.glaucusavinash.ExampleModels.Eexamples;
import com.example.glaucusavinash.Models.Example;
import com.example.glaucusavinash.Models.Example2;
import com.example.glaucusavinash.Models.Weather;
import com.example.glaucusavinash.NewModel.Exampli;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


//    @GET("https://api.openweathermap.org/data/2.5/weather?q=lucknow&appid=e1991533deb53055ab7b41c85f79680d")
//    private getAll();

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Example> getMainData(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Example2> getWeatherDesc(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Eexamples> getEexamples(@Query("q") String name);

    @GET("?&appid=e1991533deb53055ab7b41c85f79680d")
    Call<Exampli> getExampli(@Query("q") String name);
}
