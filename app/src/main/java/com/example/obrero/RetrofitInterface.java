package com.example.obrero;

import com.example.obrero.Model.Categories;
import com.example.obrero.Model.Commande;
import com.example.obrero.Model.LoginResult;
import com.example.obrero.Model.Prestation;
import com.example.obrero.Model.note;

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

    @GET("/prestation/{id}")
    Call<List<Prestation>> getPresById(@Path("id") int idPres);

    @GET("/Categories")
    Call<List<Categories>> getCategories();

    @GET("/prestation")
    Call<List<Prestation>> getAllPrestation();

    @GET("/proo")
    Call<List<LoginResult>> getAllPro();

    @GET("/Users")
    Call<List<LoginResult>> getAllUsers();

    @GET("/pres/{nomC}")
    Call<List<Prestation>> getPrestation(@Path("nomC") String nomC);

    @POST("/getpro/{id}")
    Call<Void> getPro(@Body HashMap<String, String> map, @Path("id") int idUser);

    @POST("/commande/{idUs}&{idP}")
    Call<Void> commander(@Path("idUs") int idUs, @Path("idP") int idP);

    @DELETE("/Users/{id}")
    Call<ResponseBody>  deleteUsers(@Path("id") int idUser);

    @GET("/commande/{idUs}")
    Call<List<Commande>> mesCommandes(@Path("idUs") int idUs);

    @POST("/note/{idU}&{idP}")
    Call<List<note>> noter(@Body HashMap<String, Float> map, @Path("idU") int idU, @Path("idP") int idP);

    @DELETE("/delcomm/{idC}")
    Call<ResponseBody>  deleteCommande(@Path("idC") int idC);


}