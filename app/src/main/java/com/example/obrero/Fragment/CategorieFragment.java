package com.example.obrero.Fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obrero.Model.Categories;
import com.example.obrero.Model.Prestation;
import com.example.obrero.R;
import com.example.obrero.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategorieFragment extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    private String test6;
    private int test;

    Spinner spinner;
    String test5;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categorie, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle1 = this.getArguments();
        int i = bundle1.getInt("key");
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        List<String> list = new ArrayList<String>();
        Call<List<Categories>> call = retrofitInterface.getCategories();
        call.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.code() == 200) {
                    List<Categories> categories = response.body();

                    for (Categories post : categories) {

                        test5 = post.getNom();
                        list.add(test5);
                        spinner = getActivity().findViewById(R.id.spinner2);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String item = (String) spinner.getItemAtPosition(position);
                                Call<List<Prestation>> call1 = retrofitInterface.getAllPrestation();
                                call1.enqueue(new Callback<List<Prestation>>() {
                                    @Override
                                    public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                                        if (response.code() == 200) {
                                            List<Prestation> prestations = response.body();
                                            for (Prestation pres : prestations) {

                                                test6 = pres.getNomC();
                                                test = pres.getIdPres();
                                                PrestationByCatFragment fragment = new PrestationByCatFragment();

                                                if (test6.equals(item)){

                                                    Bundle bundle1 = new Bundle();
                                                    bundle1.putString("key2", item);
                                                    bundle1.putInt("key3", test);
                                                    bundle1.putInt("key", i);
                                                    fragment.setArguments(bundle1);
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .replace(R.id.fragment_categorie, fragment, "test")
                                                            .addToBackStack(null)
                                                            .commit();
                                                }
                                                    else {

                                                }


                                            }
                                        }
                                    }


                                    @Override
                                    public void onFailure(Call<List<Prestation>> call, Throwable t) {
                                        Toast.makeText(getActivity(),
                                                "erreurRR", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }
                if (response.code() == 404) {
                    Toast.makeText(getActivity(),
                            "erreur", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreure", Toast.LENGTH_LONG).show();
            }
        });

    }





}
