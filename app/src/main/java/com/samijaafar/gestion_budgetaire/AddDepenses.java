package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddDepenses extends AppCompatActivity {
    DatabaseHelper myDb;
    Button addDepense;
    Spinner sp;
    TextView depenseType,valeur;
    DatePicker datepick;
    SimpleDateFormat dateFormat;
    Date d;
    Calendar c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_depenses);

        myDb = new DatabaseHelper(this);
        ArrayList<String> nameList = initializeSpinner();
        System.out.println(nameList);
        sp = findViewById(R.id.spinner);
        datepick = findViewById(R.id.datepick2);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        depenseType = findViewById(R.id.depenseType);
        valeur = findViewById(R.id.valeur);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,nameList);
        sp.setAdapter(adapter);
        addDepense = findViewById(R.id.addDepense);
        addDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memSelected = sp.getSelectedItem().toString();
                String DepenseType = depenseType.getText().toString();
                String Valeur = valeur.getText().toString();
                c= Calendar.getInstance();
                c.set(Calendar.YEAR, datepick.getYear());
                c.set(Calendar.MONTH, datepick.getMonth());
                c.set(Calendar.DAY_OF_MONTH, datepick.getDayOfMonth());
                d = c.getTime();

                if(memSelected.length()==0 || DepenseType.length()==0 || Valeur.length()==0){
                    TastyToast.makeText(AddDepenses.this,"VEUILLEZ REMPLIR DES DONNÉES VALIDES",TastyToast.LENGTH_LONG,TastyToast.CONFUSING);
                    return;
                }
                boolean isInserted = myDb.insertDataTb2(DepenseType,Valeur,memSelected.charAt(0)+"", dateFormat.format(d));
                if(isInserted)
                    TastyToast.makeText(AddDepenses.this, "Dépense Ajoutée",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                else
                    TastyToast.makeText(AddDepenses.this,"Dépense Non Ajoutée",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        });
    }

    public ArrayList<String> initializeSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDb.getAllData();
            if(res.getCount()==0){
                return list;
            }else{
                while(res.moveToNext()){
                    String memId = res.getString(0);
                    String memNom = res.getString(1);
                    list.add(memId+"."+memNom);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }
}
