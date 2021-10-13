package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Accueil extends AppCompatActivity {
    Button btnmenu,depenseGraph,radardepenseGraph,membres,addDepense,modifierDepense,deleteDepense,BudgetDetails,closeApp;
    RelativeLayout maincontent;
    LinearLayout mainmenu,mainHome;
    Animation fromTop,fromBottom,fromBottomSlow;
    TextView appName,titreDisplay;

    Cursor cursor;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        myDb = new DatabaseHelper(this);
        cursor = myDb.getAllData();
        titreDisplay = findViewById(R.id.titreDisplay);
        cursor.moveToFirst();
        String titre = cursor.getString(2);
        titreDisplay.setText(titre);

        btnmenu = findViewById(R.id.btnmenu);
        maincontent = findViewById(R.id.maincontent);
        mainmenu = findViewById(R.id.mainmenu);
        mainHome = findViewById(R.id.mainHome);

        depenseGraph = findViewById(R.id.depensePieGraph);
        radardepenseGraph = findViewById(R.id.depenseRadarGraph);
        membres = findViewById(R.id.membres);
        addDepense = findViewById(R.id.addDepense);
        modifierDepense = findViewById(R.id.modifyDepense);
        deleteDepense = findViewById(R.id.deleteDepense);
        BudgetDetails = findViewById(R.id.titreDetail);
        closeApp = findViewById(R.id.closeApp);
        appName  = findViewById(R.id.appNom);



        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromBottomSlow = AnimationUtils.loadAnimation(this,R.anim.frombottomslow);


        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maincontent.animate().translationX(0);
                mainmenu.animate().translationX(0);

                depenseGraph.startAnimation(fromBottom);
                radardepenseGraph.startAnimation(fromBottom);
                membres.startAnimation(fromBottom);
                addDepense.startAnimation(fromBottom);
                modifierDepense.startAnimation(fromBottom);
                deleteDepense.startAnimation(fromBottom);
                BudgetDetails.startAnimation(fromBottom);
                closeApp.startAnimation(fromBottom);
                appName.startAnimation(fromTop);
            }
        });

        maincontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maincontent.animate().translationX(-mainmenu.getWidth());
                mainmenu.animate().translationX(-mainmenu.getWidth());
            }
        });
        membres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,Membres.class);
                startActivity(intent);
            }
        });
        addDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,AddDepenses.class);
                startActivity(intent);
            }
        });
        modifierDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,ModificationDepenses.class);
                startActivity(intent);
            }
        });
        deleteDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,SuppressionDepenses.class);
                startActivity(intent);
            }
        });
        depenseGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,GrapheDepense.class);
                startActivity(intent);
            }
        });

        radardepenseGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,RadarGrapheDepense.class);
                startActivity(intent);
            }
        });

        BudgetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this,BudgetDetails.class);
                startActivity(intent);
            }
        });
        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_Builder = new AlertDialog.Builder(Accueil.this);
                a_Builder.setMessage("Are you sure you want to close the app ?").setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT",true);
                        startActivity(intent);
                        finish();
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

    @Override
    public void onBackPressed() {

    }
}
