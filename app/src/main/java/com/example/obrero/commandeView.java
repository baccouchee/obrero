package com.example.obrero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class commandeView extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    private DrawerLayout drawer;

    TextView NomPres;
    TextView DescPres;
    TextView TarifPres;
    TextView adresse;
    Button suppC;
    ImageView iv;
    RequestOptions option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        setContentView(R.layout.activity_commande_view);
        NomPres = this.findViewById(R.id.nomPresC);
        DescPres = this.findViewById(R.id.descPresC);
        TarifPres = this.findViewById(R.id.tarifPresC);
        iv = this.findViewById(R.id.containerimage1C);
               adresse = this.findViewById(R.id.localisationC);

suppC = this.findViewById(R.id.suppC);
        Intent i = getIntent();
        Bundle e = i.getExtras();
        System.out.println(e.getString("commande_description"));
        DescPres.setText(e.getString("commande_description"));
        NomPres.setText(e.getString("commande_name"));
        Commande c = new Commande();

        c.setNomP(NomPres.toString());
        c.setDescription(DescPres.toString());
        c.setTarif(e.getFloat("commande_tarif"));
        c.setIdUs(e.getInt("commande_idU"));
        c.setIdP(e.getInt("commande_idPres"));
        c.setAdresse(e.getString("commande_adresse"));
        TarifPres.setText(c.getTarif() +"DT");
        adresse.setText(c.getAdresse());

        Glide.with(this).load("http://10.0.2.2:3000/img/"+e.getString("commande_img")).centerCrop()
                .placeholder(R.drawable.ic_launcher_background).into(iv);

        suppC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> calla = retrofitInterface.deleteCommande(e.getInt("comm_id"));
                calla.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {

                            Toast.makeText(getBaseContext(),
                                    "Votre commande a été desactiver", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);

                        }
                        if (response.code() == 404) {
                            Toast.makeText(getBaseContext(),
                                    "erreur", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(),
                                "erreur", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}