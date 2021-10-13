package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

public class AjoutMembres extends AppCompatActivity {

    DatabaseHelper myDb;
    String Titre;
    String GroupTaille;
    int Taille;

    public void AddingMember(int i){
        final Button addMember = findViewById(R.id.addMembre);
        if(i==Taille){
            addMember.setText("Soumettre");
        }
        if(i>Taille){
            return;
        }
        final int id = i;
        final TextView membreNom = findViewById(R.id.membreNom);
        membreNom.setHint(id+". Nom de Membre");
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(membreNom.getText().length()==0) {
                    TextView warningText = findViewById(R.id.warningText);
                    warningText.setText("Veuillez saisir un nom de membre valide");
                    return;
                }
                boolean isInserted = myDb.insertDataTb1(membreNom.getText().toString(),Titre);
                if(isInserted)
                    TastyToast.makeText(AjoutMembres.this,"Membre Ajouté",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                else
                    TastyToast.makeText(AjoutMembres.this,"Membre Non Ajouté",TastyToast.LENGTH_LONG,TastyToast.ERROR);

                System.out.println(membreNom.getText().toString()+id);
                if(addMember.getText().equals("Soumettre")){
                    Intent intent = new Intent(AjoutMembres.this,Accueil.class);
                    startActivity(intent);
                }
                membreNom.setText("");
                AddingMember(id+1);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_membres);
        myDb = new DatabaseHelper(this);
        Titre = getIntent().getStringExtra("Titre");
        GroupTaille = getIntent().getStringExtra("GroupTaille");
        Taille = Integer.parseInt(GroupTaille);
        AddingMember(1);
    }
}
