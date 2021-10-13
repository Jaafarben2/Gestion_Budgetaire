package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class BudgetDetails extends AppCompatActivity {

    DatabaseHelper myDb;
    Cursor cursor;
    TextView budgetNom,totalDepense;
    ListView membreListe;
    Button endBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_details);
        myDb = new DatabaseHelper(this);
        cursor = myDb.getAllData();
        endBudget = findViewById(R.id.endBudget);
        budgetNom = findViewById(R.id.budgetNom);
        totalDepense = findViewById(R.id.totalDepense);
        cursor.moveToFirst();
        String titre = cursor.getString(2);
        budgetNom.setText(titre);
        membreListe = findViewById(R.id.membreListe);
        List<String> lst = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String member = cursor.getString(1);
                String id = cursor.getString(0);
                lst.add(id+". "+member);
            }while (cursor.moveToNext());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lst);
        membreListe.setAdapter(arrayAdapter);
        membreListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String membre = membreListe.getItemAtPosition(position).toString();
                TastyToast.makeText(BudgetDetails.this,""+membre,TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
            }
        });
        cursor = myDb.getAllDepense();
        int sum=0;
        if(cursor.moveToFirst()){
            do{
                Integer value = Integer.parseInt(cursor.getString(2));
                sum+=value;
            }while (cursor.moveToNext());
        }
        totalDepense.setText("Budget total des dépenses: MAD "+sum);
        endBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_Builder = new AlertDialog.Builder(BudgetDetails.this);
                a_Builder.setMessage("Êtes-vous sûr de vouloir terminer le budget et fermer l'application ?").setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDropped = myDb.dropDatabase();
                        if(isDropped){
                            TastyToast.makeText(BudgetDetails.this,"Budget terminé avec succès",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT",true);
                            startActivity(intent);
                            finish();
                        }else{
                            TastyToast.makeText(BudgetDetails.this,"Erreur dans la fin de Budget",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            dialog.cancel();
                        }
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = a_Builder.create();
                alertDialog.setTitle("Alerte !!!");
                alertDialog.show();
            }
        });
    }
}
