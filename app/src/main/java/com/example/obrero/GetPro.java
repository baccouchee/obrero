package com.example.obrero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPro extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    private DrawerLayout drawer;

    TextView NomPres;
    TextView DescPres;
    TextView TarifPres;
    Button commander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.comm);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //The key argument here must match that used in the other activity


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle extras = getIntent().getExtras();
        Bundle extras1 = getIntent().getExtras();
        int value = extras.getInt("key4");
        int value1 = extras1.getInt("key");
        System.out.println("id" + value1);
        System.out.println(value);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        NomPres = this.findViewById(R.id.nomPres);
        DescPres = this.findViewById(R.id.descPres);
        TarifPres = this.findViewById(R.id.tarifPres);
        commander= this.findViewById(R.id.commander);

        Call<List<Prestation>> call = retrofitInterface.getPresById(value);
        call.enqueue(new Callback<List<Prestation>>() {
            @Override
            public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                if (response.code() == 200) {
                    List<Prestation> prestations = response.body();
                    for (Prestation post : prestations){
                        String content = "";
                        String content2 = "";
                        String content3 = "";
                        content2 += "" + post.getNomP();
                        content3 += "" + post.getDescription();
                        content += "Tarif: " + post.getTarif() + "DT";

                        NomPres.append(content2);
                        DescPres.append(content3);
                        TarifPres.append(content);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Prestation>> call, Throwable t) {

            }
        });


        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call1 = retrofitInterface.commander(value1, value);
                call1.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code() == 200) {
                            System.out.println("valider");
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle extras = getIntent().getExtras();
        int value2 = extras.getInt("key");
        System.out.println("test" + value2);
        switch (item.getItemId()) {
            case R.id.nav_message:
                CategorieFragment categorieFragment = new CategorieFragment();
                categorieFragment.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        categorieFragment).commit();
                break;
            case R.id.nav_chat:
                CompteFragment fragment = new CompteFragment();
                fragment.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                break;
            case R.id.nav_profile:
                LocationFragment fragment2 = new LocationFragment();
                fragment2.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment2).commit();
                break;

            case  R.id.nav_getpro:
                GetProFragment fragment1 = new GetProFragment();
                fragment1.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment1).commit();

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}