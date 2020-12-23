package com.example.obrero;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MesCommandes extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    ListView listView;
    private String test;
    private int test2;
    private String date;
    Button supp;
    Button comm;
    Button pres;
    private String nom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mescommandes, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle1 = this.getArguments();

        int ii = bundle1.getInt("key");
        supp = getActivity().findViewById(R.id.supp);
        comm = getActivity().findViewById(R.id.commande2);
        pres = getActivity().findViewById(R.id.pres);

        supp.setVisibility(View.GONE);
        comm.setVisibility(View.GONE);
        pres.setVisibility(View.GONE);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        List<HashMap<String, String>> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        listView = getActivity().findViewById(R.id.listcomm);
        Call<List<Commande>> call = retrofitInterface.mesCommandes(ii);
        call.enqueue(new Callback<List<Commande>>() {
            @Override
            public void onResponse(Call<List<Commande>> call, Response<List<Commande>> response) {

                if (response.code() == 200) {
                    List<Commande> commandes = response.body();
                    for (Commande commande : commandes){
                    nom = commande.getNomP();
                    date = commande.getDate().toString();
                    list3.add(nom);

                        System.out.println("test" + nom);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1,list3);
                    listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Commande>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });

    }
}
