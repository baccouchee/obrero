package com.example.obrero;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    ListView listView;
    private String test;
    private int test2;
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
        int ii = bundle1.getInt("key3");
        int iii = bundle1.getInt("key");
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        List<String> list2 = new ArrayList<String>();
        listView = getActivity().findViewById(R.id.listcom);
        Call<List<Prestation>> call = retrofitInterface.getPrestation(i);
        call.enqueue(new Callback<List<Prestation>>() {
            @Override
            public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                if (response.code() == 200) {

                    List<Prestation> prestations = response.body();
                    for (Prestation pres : prestations) {
                      test = pres.getNomP();
                      list2.add(test);
                        System.out.println(test);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1,list2);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String item = (String) listView.getItemAtPosition(position);
                                System.out.println(ii);
                                Call<List<Prestation>> call1 = retrofitInterface.getPresById(ii);
                                    call1.enqueue(new Callback<List<Prestation>>() {
                                        @Override
                                        public void onResponse(Call<List<Prestation>> call, Response<List<Prestation>> response) {
                                            if (response.code() == 200) {
                                                List<Prestation> prestations = response.body();
                                                for (Prestation pres : prestations) {
                                                    test2 = pres.getIdPres();

                                                    if (test2 == ii){
                                                        ShowPresFragment fragment2 = new ShowPresFragment();
                                                        Bundle bundle1 = new Bundle();


                                                        Intent intent = new Intent();
                                                        Intent intent2 = new Intent();
                                                        intent.setClass(getActivity(), GetPro.class);
                                                        intent.putExtra("key4",ii);
                                                        intent.putExtra("key", iii);
                                                        System.out.println("test"+iii);
                                                        getActivity().startActivity(intent);


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
                    listView.setVisibility(View.GONE);
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
