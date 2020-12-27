package com.example.obrero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPro extends AppCompatActivity{

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    private DrawerLayout drawer;

    TextView NomPres;
    TextView DescPres;
    TextView TarifPres;
    Button commander;
    ImageView iv;
    RequestOptions option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.comm);
      //  NavigationView navigationView = findViewById(R.id.nav_view);
       //        navigationView.setNavigationItemSelectedListener(this);
      //  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
              //  R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //The key argument here must match that used in the other activity


        //drawer.addDrawerListener(toggle);
       // toggle.syncState();

        Bundle extras = getIntent().getExtras();
        Bundle extras1 = getIntent().getExtras();
        int value = extras.getInt("key4");
           int value1 = extras1.getInt("key");


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        setContentView(R.layout.activity_get_pro);
        NomPres = this.findViewById(R.id.nomPres);
        DescPres = this.findViewById(R.id.descPres);
        TarifPres = this.findViewById(R.id.tarifPres);
        iv = this.findViewById(R.id.containerimage);

        Intent i = getIntent();
        Bundle e = i.getExtras();
        System.out.println(e.getString("campaign_description"));
        DescPres.setText(e.getString("campaign_description"));
        NomPres.setText(e.getString("campaign_name"));
        Prestation c = new Prestation();
        c.setNomP(NomPres.toString());
        c.setDescription(DescPres.toString());
        c.setTarif(e.getFloat("campaign_tarif"));
        c.setIdU(e.getInt("campaign_idU"));
        c.setIdPres(e.getInt("campaign_idPres"));
        TarifPres.setText("tarif" + c.getTarif());
        System.out.println("id user = " + c.getIdU());
        System.out.println("id pres = " + c.getIdPres());
        Glide.with(this).load("http://10.0.2.2:3000/img/"+e.getString("campaign_img")).centerCrop()
                .placeholder(R.drawable.rectangle).into(iv);

        commander= this.findViewById(R.id.commander);
        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call1 = retrofitInterface.commander(c.getIdU(), c.getIdPres());
                call1.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code() == 200) {
                            Toast.makeText(GetPro.this, "Votre commande a bien été passer", Toast.LENGTH_LONG).show();
                        }
                        if (response.code()== 404){
                            Toast.makeText(GetPro.this, "Vous avez deja passer votre commande", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        System.out.println("err");
                    }
                });
            }
        });
    }


}