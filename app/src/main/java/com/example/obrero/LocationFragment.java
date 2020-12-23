package com.example.obrero;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    LatLng p1 = null;
    List<Address> address;
    String nomP;
    String adresse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_maps, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder coder = new Geocoder(getContext());


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<LoginResult>> call = retrofitInterface.getAllPro();
        call.enqueue(new Callback<List<LoginResult>>() {
            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {
                if (response.code() == 200) {
                    List<LoginResult> users = response.body();
                    for (LoginResult user : users) {
                        adresse = user.getAdresse();
                        nomP = user.getNomP();
                        System.out.println(adresse);
                        System.out.println(nomP);
                        try {
                            address = coder.getFromLocationName(adresse, 5);
                            Address location = address.get(0);
                            p1 = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions options = new MarkerOptions();
                            options.position(p1).title(nomP);
                            mMap.addMarker(options);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
                        }
                        catch (IOException ex) {

                            ex.printStackTrace();
                        }





                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "erreur", Toast.LENGTH_LONG).show();
            }
        });



//double lat = getLocationFromAddress(getActivity(),"Sousse,Tunisia").latitude;
        //double longi = getLocationFromAddress(getActivity(),"Sousse,Tunisia").longitude;
            //address = coder.getFromLocationName("Sousse, Tunisia", 5);
            //Address location = address.get(0);
            //double latitude = location.getLatitude();
            //double langitude = location.getLongitude();
            //System.out.println(langitude);
            //System.out.println(latitude);


    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;


        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
