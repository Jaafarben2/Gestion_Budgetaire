package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button demarrer;
    ImageView LogoPrincipale;
    Animation rotation,agrandissement;
    TextView appNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getBooleanExtra("EXIT",false)){
            finish();
        }
        appNom = findViewById(R.id.appNom);
        LogoPrincipale = findViewById(R.id.LogoPrincipale);

        agrandissement = AnimationUtils.loadAnimation(this,R.anim.scale);
        rotation = AnimationUtils.loadAnimation(this,R.anim.rotate);

        appNom.startAnimation(agrandissement);
        LogoPrincipale.startAnimation(rotation);
        demarrer = findViewById(R.id.demarrer);
        demarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TitreSelection.class);
                startActivity(i);
            }
        });
    }
}