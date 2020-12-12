package com.example.obrero;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup(@Body HashMap<String, String> map);

    @POST("/prestation/{id}")
    Call<Void> addPrestation(@Body HashMap<String, String> map, @Path("id") int idUser);

    @GET("/Users/{id}")
    Call<List<LoginResult>> getUsers(@Path("id") int idUser);

    @GET("/Categories")
    Call<List<Categories>> getCategories();

    @POST("/getpro/{id}")
    Call<Void> getPro(@Body HashMap<String, String> map, @Path("id") int idUser);


    @DELETE("/Users/{id}")
    Call<ResponseBody>  deleteUsers(@Path("id") int idUser);

}