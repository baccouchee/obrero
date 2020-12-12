package com.example.obrero;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

    ListView listView;
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
                        listView = getActivity().findViewById(R.id.listView);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1,list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String item = (String) listView.getItemAtPosition(position);
                                Call<List<Prestation>> call1 = retrofitInterface.getAllPrestation();
                                call1.enqueue(new Callback<List<Prestation>>() {
                                    @Override
                                    public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                                        if (response.code() == 200) {
                                            List<Prestation> prestations = response.body();
                                            for (Prestation pres : prestations) {
                                                test6 = pres.getNomC();
if (test6.equals(item)){
    PrestationByCatFragment fragment = new PrestationByCatFragment();
    getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_categorie, fragment, "findThisFragment")
            .addToBackStack(null)
            .commit();

          }

                                            }
                                        }
                                    }


                                    @Override
                                    public void onFailure(Call<List<Prestation>> call, Throwable t) {
                                        Toast.makeText(getActivity(),
                                                "erreur", Toast.LENGTH_LONG).show();
                                    }
                                });
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
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });

    }
}
