package com.example.obrero;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obrero.Adapter.RecyclerViewAdapterSeller;
import com.example.obrero.Fragment.CategorieFragment;
import com.example.obrero.Fragment.CompteFragment;
import com.example.obrero.Fragment.GetProFragment;
import com.example.obrero.Fragment.LocationFragment;
import com.example.obrero.Model.Categories;
import com.example.obrero.Model.LoginResult;
import com.example.obrero.Model.Prestation;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    TextView nom;
    TextView mail;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private int value;
    Bundle extras;
    RecyclerView listView;
    EditText searchView;
    CharSequence search="";
    private List<Prestation> lstPres = new ArrayList<>();
    RecyclerViewAdapterSeller myAdapter;
    private List<Categories> lstCat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.comm);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        listView = findViewById(R.id.bestseller);
        Call<List<Prestation>> call = retrofitInterface.getAllPrestation();
        call.enqueue(new Callback<List<Prestation>>() {
            @Override
            public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                if (response.code() == 200) {

                    List<Prestation> prestations = response.body();
                    for (Prestation pres : prestations) {
                        Prestation p = new Prestation();
                        p.setAdresse(pres.getAdresse());
                        p.setNomP(pres.getNomP());
                        p.setDescription(pres.getDescription());

                        p.setPhoto(pres.getPhoto());

                        p.setTarif(pres.getTarif());
                        p.setIdPres(pres.getIdPres());
                        p.setIdU(pres.getIdU());
                        listView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL, false));
                        myAdapter = new RecyclerViewAdapterSeller(getBaseContext(), lstPres);
                        lstPres.add(p);
                        listView.setAdapter(myAdapter);

                    }

                    if (response.code() == 404) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Prestation>> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        mail =findViewById(R.id.mail);
        nom = findViewById(R.id.nom);
        extras = getIntent().getExtras();
        value = extras.getInt("key");

        extras.putInt("key", value);
        Call<List<LoginResult>> call = retrofitInterface.getUsers(value);
        call.enqueue(new Callback<List<LoginResult>>() {
            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {
                if (response.code() == 200) {
                    List<LoginResult> user = response.body();

                    for (LoginResult post : user) {
                        String content = "";
                        String content2 = "";
                        content2 += "" + post.getNom();
                        content += "" + post.getEmail() + "\n";

                        nom.append(""+ content2);
                        mail.append(""+ content);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {

            }
        });



        switch (item.getItemId()) {
            case R.id.nav_menu:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("key", value);
                startActivity(intent);

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
                break;

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