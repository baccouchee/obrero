package com.example.obrero;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrestationFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private String test5;
    private String test3;
    private String test4;
    private String test1;
    private String test2;

    EditText tarifP;
    EditText nomP;
    EditText descP;
    EditText photoP;
    Button supp1;
    Button supp2;
    Spinner dropdown;
    Button vald;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_prestation, container, false);
        return v;

    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supp1= getActivity().findViewById(R.id.pres);
        supp2= getActivity().findViewById(R.id.supp);
        Bundle bundle1 = this.getArguments();
        int i = bundle1.getInt("key1");
        supp1.setVisibility(View.GONE);
        supp2.setVisibility(View.GONE);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        tarifP= getActivity().findViewById(R.id.tarifP);
        nomP= getActivity().findViewById(R.id.nomP);
        descP= getActivity().findViewById(R.id.descP);
        photoP= getActivity().findViewById(R.id.photoP);

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
                        System.out.println(list);
                        dropdown = getActivity().findViewById(R.id.spinner1);

                        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,list);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dropdown.setAdapter(adapter);

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




        vald= getActivity().findViewById(R.id.vald);

        vald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                test1 = nomP.getText().toString();
                map.put("Nom", test1);

                test2= descP.getText().toString();
                map.put("description", test2);

                test3 = tarifP.getText().toString();
                map.put("tarif", test3);

                test4= photoP.getText().toString();
                map.put("photo", test4);

                map.put("nomC", dropdown.getSelectedItem().toString());
                //get the spinner from the xml.

                if (test1.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre Nom>", Toast.LENGTH_LONG).show();}

                else if (test2.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre description", Toast.LENGTH_LONG).show();}

                else if (test3.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre tarif", Toast.LENGTH_LONG).show();}

                else if (test4.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre photo", Toast.LENGTH_LONG).show();}


                else {

                    Call<Void> call = retrofitInterface.addPrestation(map,i);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getActivity(),
                                        "Prestation ajouter", Toast.LENGTH_LONG).show();
                            } else if (response.code() == 404) {
                                Toast.makeText(getActivity(),
                                        "err", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });}


            }
        });





    }

}
