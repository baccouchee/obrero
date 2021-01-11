package com.example.obrero.Fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obrero.Adapter.RecyclerViewAdapter;
import com.example.obrero.Model.Prestation;
import com.example.obrero.R;
import com.example.obrero.RetrofitInterface;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrestationByCatFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    RecyclerView listView;
    EditText searchView;
    CharSequence search="";
    private List<Prestation> lstPres = new ArrayList<>();
    RecyclerViewAdapter myAdapter;
    TextView note;
    float somme = 0, moy;
    public ArrayList<Float> liste;
    public int size;
    float notee=0;
    int is2=1;
   float test;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prestationbycat, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle1 = this.getArguments();
        String i = bundle1.getString("key2");

        int value1 = bundle1.getInt("key");
        bundle1.putInt("key", value1);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


         liste = new ArrayList<>();



        searchView = getActivity().findViewById(R.id.search_bar);
        listView = getActivity().findViewById(R.id.listcom);
        Call<List<Prestation>> call = retrofitInterface.getPrestation(i);
        call.enqueue(new Callback<List<Prestation>>() {
            @Override
            public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                if (response.code() == 200) {

                    List<Prestation> prestations = response.body();
                    is2++;
                    for (Prestation pres : prestations) {

                        Call<List<Prestation>> call1 = retrofitInterface.nbnote(pres.getIdPres());
                        call1.enqueue(new Callback<List<Prestation>>() {
                            @Override
                            public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                                List<Prestation> prestationns = response.body();
                                for (Prestation pres1 : prestationns) {

                                    liste.add(pres1.getNote());
                                    size = liste.size();


                                }

                                for(int i = 0; i < size; i++)
                                {
                                 somme += liste.get(i);
                                 moy = somme / size;
                                }

                                notee = moy/is2;
                                DecimalFormat df = new DecimalFormat();
                                df.setMaximumFractionDigits(1);

                                note = getActivity().findViewById(R.id.note);
                                test = Float.parseFloat(df.format(notee));

                                Prestation p = new Prestation();
                                p.setNote(test);
                                p.setIdConnecter(value1);
                                p.setAdresse(pres.getAdresse());
                                p.setNomP(pres.getNomP());
                                p.setDescription(pres.getDescription());
                                p.setPhoto(pres.getPhoto());
                                p.setTarif(pres.getTarif());
                                p.setIdPres(pres.getIdPres());
                                p.setIdU(pres.getIdU());
                                System.out.println("looool" + p.getNote());
                                listView.setLayoutManager(new LinearLayoutManager(getContext()));
                                myAdapter = new RecyclerViewAdapter(getContext(),lstPres) ;
                                lstPres.add(p);
                                listView.setAdapter(myAdapter);

                            }


                            @Override
                            public void onFailure(Call<List<Prestation>> call, Throwable t) {

                            }
                        });



                        searchView.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                myAdapter.getFilter().filter(s);
                                search = s;

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                }

                if (response.code() == 404) {

                }
            }

            @Override
            public void onFailure(Call<List<Prestation>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });



    }



}
