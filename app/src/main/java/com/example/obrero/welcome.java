package com.example.obrero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class welcome extends AppCompatActivity {

    Button connexion;
    TextView inscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        connexion = findViewById(R.id.connexion);
        inscription = findViewById(R.id.inscription);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this, login.class);
                startActivity(intent);
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this, inscription.class);
                startActivity(intent);
            }
        });

    }
}