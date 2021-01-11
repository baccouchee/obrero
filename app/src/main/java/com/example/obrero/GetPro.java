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
import com.example.obrero.Model.Prestation;
import com.example.obrero.Model.note;

import java.util.HashMap;
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
    TextView adresse;
    Button commander;
    ImageView iv;
    RequestOptions option;
    RatingBar ratingBar;
    float myRating = 0;
    private int value;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.comm);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        setContentView(R.layout.activity_get_pro);
        NomPres = this.findViewById(R.id.nomPres);
        DescPres = this.findViewById(R.id.descPres);
        TarifPres = this.findViewById(R.id.tarifPres);
        iv = this.findViewById(R.id.containerimage1);
        ratingBar = this.findViewById(R.id.ratingBar);
        adresse = this.findViewById(R.id.localisation);


        Intent i = getIntent();
        Bundle e = i.getExtras();


                DescPres.setText(e.getString("campaign_description"));
        NomPres.setText(e.getString("campaign_name"));
        int ii = e.getInt("key");

        Prestation c = new Prestation();
        c.setNomP(NomPres.toString());
        c.setDescription(DescPres.toString());
        c.setTarif(e.getFloat("campaign_tarif"));
        c.setIdU(e.getInt("campaign_idU"));
        c.setIdPres(e.getInt("campaign_idPres"));
        c.setAdresse(e.getString("campaign_adresse"));
        TarifPres.setText(c.getTarif() +"DT");
        adresse.setText(c.getAdresse());

        Glide.with(this).load("http://10.0.2.2:3000/img/"+e.getString("campaign_img")).centerCrop()
                .placeholder(R.drawable.ic_launcher_background).into(iv);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                int rating = (int) v;
                String message = null;
                myRating = ratingBar.getRating();
                HashMap<String, Float> map = new HashMap<>();
                map.put("note", myRating);
                Call<List<note>> call = retrofitInterface.noter(map,c.getIdU(), c.getIdPres());
                call.enqueue(new Callback<List<note>>() {
                    @Override
                    public void onResponse(Call<List<note>> call, Response<List<note>> response) {
                        if (response.code() == 200){
                            Toast.makeText(GetPro.this,
                                    "Merci d'avoir noter" + myRating, Toast.LENGTH_LONG).show();
                        }
                        else if (response.code() == 404) {
                            Toast.makeText(GetPro.this,
                                    "Merci d'avoir noter", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<note>> call, Throwable t) {
                        Toast.makeText(GetPro.this,
                                "Merci d'avoir noter", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        commander= this.findViewById(R.id.commander);
        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call1 = retrofitInterface.commander(ii, c.getIdPres());
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

                    }
                });
            }
        });
    }


}