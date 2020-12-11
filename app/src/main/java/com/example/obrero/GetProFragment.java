package com.example.obrero;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GetProFragment extends Fragment {

Button suivant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_getpro, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suivant = getActivity().findViewById(R.id.suivant);
        suivant.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                GetProoFragment nextFrag= new GetProoFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_getprooo, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

                suivant.setVisibility(View.GONE);
            }
        });


    }
}