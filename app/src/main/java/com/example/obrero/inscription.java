package com.example.obrero;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class inscription extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private String test;
    private String test2;
    private String test3;
    private String test4;
    private String test5;

    Button inscriptionn;
    EditText email1;
    EditText mdp1;
    EditText mdp2;
    EditText adresse1;
    EditText nom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        inscriptionn = findViewById(R.id.inscriptionn);
        email1 = findViewById(R.id.email1);
        mdp1 = findViewById(R.id.mdp1);
        mdp2 = findViewById(R.id.mdp2);
        adresse1 = findViewById(R.id.adresse1);
        nom1 = findViewById(R.id.nom1);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        inscriptionn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> map = new HashMap<>();

                test3 = email1.getText().toString();
                map.put("email", test3);
                test4 = nom1.getText().toString();
                map.put("nom", test4);
                test5 = adresse1.getText().toString();
                map.put("adresse", test5);

                test2 = mdp1.getText().toString();
                map.put("password", test2);
                test = mdp2.getText().toString();

                if (test3.equals("")){Toast.makeText(inscription.this,
                        "Verfifier votre email", Toast.LENGTH_LONG).show();}

                   else if (test4.equals("")){Toast.makeText(inscription.this,
                        "Verfifier votre nom", Toast.LENGTH_LONG).show();}

                       else if (test5.equals("")){Toast.makeText(inscription.this,
                        "Verfifier votre adresse", Toast.LENGTH_LONG).show();}

                             else  if (!test2.equals(test) || test2.equals("")) {
                             Toast.makeText(inscription.this,
                            "Verfifier mdp", Toast.LENGTH_LONG).show(); }
                else {

                    Call<Void> call = retrofitInterface.executeSignup(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            if (response.code() == 200) {
                                Toast.makeText(inscription.this,
                                        "Signed up successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(inscription.this, login.class);
                                startActivity(intent);
                            } else if (response.code() == 404) {
                                Toast.makeText(inscription.this,
                                        "Already registered", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(inscription.this, t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });}

            }
        });
    }
}