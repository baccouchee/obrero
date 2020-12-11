package com.example.obrero;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompteFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private int idU;

    TextView adresse3;
    TextView prenom;
    TextView pemail;
    Button supp;

    Button pres;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_compte, container, false);
        return v;

    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        int i = bundle.getInt("key");


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        adresse3 = getActivity().findViewById(R.id.adresse3);
        prenom = getActivity().findViewById(R.id.prenom);
        pemail = getActivity().findViewById(R.id.pemail);
        supp = getActivity().findViewById(R.id.supp);
        idU = getId();
        System.out.println(idU);

        Call<List<LoginResult>> call = retrofitInterface.getUsers(i);
        call.enqueue(new Callback<List<LoginResult>>() {

            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {
                if (response.code() == 200) {
                    List<LoginResult> user = response.body();

                    for (LoginResult post : user) {
                        String content = "";
                        String content2 = "";
                        String content3 = "";
                        content2 += "" + post.getNom();
                        content3 += "" + post.getEmail();
                        content += "Votre adresse :" + post.getAdresse() + "\n";


                        prenom.append(content2);
                        adresse3.append(content);
                        pemail.append(content3);

                    }


supp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Call<ResponseBody> calla = retrofitInterface.deleteUsers(i);
        calla.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    Toast.makeText(getActivity(),
                            "Votre compte a été desactiver", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), login.class);
                    startActivity(intent);

                }
                if (response.code() == 404) {
                    Toast.makeText(getActivity(),
                            "erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });
    }
});


                }



                else if (response.code() == 404) {
                    adresse3.setText("Probleme");

                }
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {
                adresse3.setText("probleme");
            }


        });


        pres = getActivity().findViewById(R.id.pres);
        pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrestationFragment fragment = new PrestationFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_compte, fragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();


            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

}
