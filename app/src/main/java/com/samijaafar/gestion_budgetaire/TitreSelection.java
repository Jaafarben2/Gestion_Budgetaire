package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

public class TitreSelection extends AppCompatActivity {

    private TextView titreTextView;
    private String Titre;
    DatabaseHelper myDb;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_titre_selection);

        myDb = new DatabaseHelper(this);
        cursor = myDb.getAllData();
        if(cursor.moveToFirst()){
            String titre = cursor.getString(2);
            Intent i = new Intent(TitreSelection.this,Accueil.class);
            startActivity(i);
            return;
        }
        final Button addTitreBtn = findViewById(R.id.addTitre);
        addTitreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titreTextView = findViewById(R.id.titre);
                TextView warningTextView = findViewById(R.id.warningText);
                if(titreTextView.getText().length()==0){
                    warningTextView.setText("Veuillez saisir un Titre valide ");
                    TastyToast.makeText(TitreSelection.this,"Oops..",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }else{
                    warningTextView.setText("");
                    Titre = titreTextView.getText().toString();
                    TastyToast.makeText(TitreSelection.this,"Titre Sélectionné",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                    Intent i = new Intent(TitreSelection.this,CreationGroupe.class);
                    i.putExtra("Titre",Titre);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
