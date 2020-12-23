package com.example.obrero;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        mail = this.findViewById(R.id.mail);
        nom = this.findViewById(R.id.nom);
        Bundle extras = getIntent().getExtras();
        int value = extras.getInt("key");
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
                        System.out.println(content);

                        nom.append(content2);
                        mail.append(content);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {

            }
        });


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