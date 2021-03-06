package com.example.obrero.Fragment;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.obrero.R;
import com.example.obrero.RetrofitInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetProoFragment extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    private String test3;
    private String test4;

    EditText debut;
    EditText fin;

    Button suivant;
    Button valider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_getproo, container, false);

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
        suivant = view.findViewById(R.id.suivant);
        debut = view.findViewById(R.id.debut);
        fin = view.findViewById(R.id.fin);
        debut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        debut.setText(selectedHour+":"+selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Horaire de début");
                mTimePicker.show();

            }
        });
        fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fin.setText(selectedHour+":"+selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Horaire de fin");
                mTimePicker.show();

            }
        });

        valider = view.findViewById(R.id.valider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

test3 = debut.getText().toString();
test4 = fin.getText().toString();
                map.put("heureDeb", test3);
                map.put("heureFin", test4);


                if (test3.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre date de debut", Toast.LENGTH_LONG).show();}

                else if (test4.equals("")){Toast.makeText(getActivity(),
                        "Verfifier votre date de fin", Toast.LENGTH_LONG).show();}

                else {

                    Call<Void> call = retrofitInterface.getPro(map, i);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getActivity(),
                                        "Upgrade to Pro", Toast.LENGTH_LONG).show();
                            }
                            else if (response.code() == 403) {
                                Toast.makeText(getActivity(),
                                        "Heure de debut doit etre inférieur a l'heure de fin", Toast.LENGTH_LONG).show();}
                            else if (response.code() == 404) {
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
