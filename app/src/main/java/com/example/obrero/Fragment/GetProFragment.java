package com.example.obrero.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obrero.R;

public class GetProFragment extends Fragment {

Button suivant;
    Bundle bundle1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_getpro, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        int i = bundle.getInt("key");


        bundle1 = new Bundle();
        bundle1.putInt("key", i);

        suivant = view.findViewById(R.id.suivant);



        suivant.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                fragReload();



            }
        });


    }

    private void fragReload() {
        GetProoFragment fragment = new GetProoFragment();
        fragment.setArguments(bundle1);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
