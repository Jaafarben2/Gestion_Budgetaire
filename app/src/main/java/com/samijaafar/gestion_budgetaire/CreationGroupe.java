package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreationGroupe extends AppCompatActivity {

    String Titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_groupe);
        Titre = getIntent().getStringExtra("Titre");
        TextView titreHeader = findViewById(R.id.titreHeader);
        titreHeader.setText("Titre: "+ Titre);

        Button sizeSelectionBtn = findViewById(R.id.tailleSelectionBtn);
        sizeSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView grps = findViewById(R.id.groupTaille);
                TextView warningText = findViewById(R.id.warningText);
                try{
                    Integer GrpTaille = Integer.parseInt(grps.getText().toString());
                    if(GrpTaille<=1){
                        warningText.setText("Veuillez saisir une taille de groupe valide");
                    }else{
                        warningText.setText("");
                        Intent i = new Intent(CreationGroupe.this,AjoutMembres.class);
                        i.putExtra("Titre",Titre);
                        i.putExtra("GroupTaille",GrpTaille.toString());
                        startActivity(i);
                    }
                }catch (Exception e){
                    warningText.setText("Veuillez saisir une taille de groupe valide\n");
                }


            }
        });
    }
}
