package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class SuppressionDepenses extends AppCompatActivity {

    DatabaseHelper myDb;
    Spinner sp_mem,sp_dep,sp_date;
    TextView depValeur,warningText;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_depenses);
        myDb = new DatabaseHelper(this);
        ArrayList<String> nameList = initializeMemSpinner();
        sp_mem = findViewById(R.id.sp_mem2);
        sp_dep = findViewById(R.id.sp_dep2);
        sp_date = findViewById(R.id.sp_date2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,nameList);
        sp_mem.setAdapter(adapter);
        deleteBtn = findViewById(R.id.deleteBtn);
        warningText = findViewById(R.id.warningText);
        sp_mem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = sp_mem.getSelectedItem().toString();
                ArrayList<String> DateDepenseList = initializeDateSpinner(memSelected.charAt(0)+"");
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SuppressionDepenses.this,R.layout.spinner_layout,R.id.txt,DateDepenseList);
                sp_date.setAdapter(adapter1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> DateDepenseList = initializeDateSpinner("1");
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SuppressionDepenses.this,R.layout.spinner_layout,R.id.txt,DateDepenseList);
                sp_date.setAdapter(adapter1);

            }
        });

        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = sp_mem.getSelectedItem().toString();
                String dateSelected = sp_date.getSelectedItem().toString();
                ArrayList<String> DepenseList = initializeDepSpinner(memSelected.charAt(0)+"",dateSelected);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuppressionDepenses.this,R.layout.spinner_layout,R.id.txt,DepenseList);
                sp_dep.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> DepenseList = initializeDepSpinner("1","");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuppressionDepenses.this,R.layout.spinner_layout,R.id.txt,DepenseList);
                sp_dep.setAdapter(adapter);
            }
        });


        sp_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = sp_mem.getSelectedItem().toString();
                String depSelected = sp_dep.getSelectedItem().toString();
                Cursor cursor = myDb.getValueByDepenseNomAndId(depSelected.substring( 0, depSelected.indexOf(".")),memSelected.charAt(0)+"");
                while(cursor.moveToNext()){
                    String Valeur = cursor.getString(2);
                    depValeur = findViewById(R.id.depValeur2);
                    depValeur.setText("Valeur actuelle: MAD "+Valeur);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int items = sp_dep.getAdapter().getCount();
                depValeur = findViewById(R.id.depValeur2);
                String value = depValeur.getText().toString();
                if(items==0){
                    warningText.setText("VOUS NE POUVEZ PAS SUPPPRIMER CES DONNÉES");
                    return;
                }else{
                    warningText.setText("");
                    String memSelected = sp_mem.getSelectedItem().toString();
                    String depSelected = sp_dep.getSelectedItem().toString();
                    boolean isDeleted = myDb.deleteByIdAndDepense(depSelected.substring( 0, depSelected.indexOf(".")),memSelected.charAt(0)+"");
                    if(isDeleted)
                        TastyToast.makeText(SuppressionDepenses.this,"Dépense Supprimée",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                    else
                        TastyToast.makeText(SuppressionDepenses.this,"Dépense Non Supprimée",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    depValeur.setText("");

                }
            }
        });
    }
    public ArrayList<String> initializeMemSpinner(){
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

    public ArrayList<String> initializeDateSpinner(String Id){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDb.getDateDepenseById(Id);
            if(res.getCount()==0){
                return list;
            }else{
                while(res.moveToNext()){
                    String Date = res.getString(0);
                    list.add(Date);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }

    public ArrayList<String> initializeDepSpinner(String Id, String Date){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDb.getDepensebyIdandDate(Id,Date);
            if(res.getCount()==0){
                depValeur = findViewById(R.id.depValeur2);
                depValeur.setText("PAS ENCORE DE DÉPENSES");
                return list;
            }else{
                while(res.moveToNext()){
                    String DepId = res.getString(0);
                    String Depense = res.getString(1);
                    list.add(DepId+"."+Depense);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }
}
