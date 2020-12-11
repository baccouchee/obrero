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

public class login extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private int idU;

    Button connexionn;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connexionn = findViewById(R.id.connexionn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);




        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        connexionn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", email.getText().toString());

                map.put("password", password.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {

                            LoginResult result = response.body();
                            idU = result.getId();
                            System.out.println(idU);
                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.putExtra("key", idU);
                            startActivity(intent);

                        } else if (response.code() == 404) {
                            Toast.makeText(login.this, "Vérifier vos paramètres", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(login.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}