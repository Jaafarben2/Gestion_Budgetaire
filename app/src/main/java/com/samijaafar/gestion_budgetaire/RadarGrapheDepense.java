package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class RadarGrapheDepense extends AppCompatActivity {

    public static final int[] COLORS = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"), Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseHelper myDb;
        Cursor initial_cursor, cursor;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_graphe_depense);
        RadarChart radarChart = findViewById(R.id.radar_chart);
        myDb = new DatabaseHelper(getApplicationContext());
        initial_cursor = myDb.getAllData();
        Set<String> set_membres = new HashSet<String>();
        if (initial_cursor.moveToFirst()) {
            do {
                String Membre = initial_cursor.getString(0);
                set_membres.add(Membre);
            } while (initial_cursor.moveToNext());
        }
        cursor = myDb.getDepenseType();
        Set<String> set_depenses = new HashSet<String>();
        if (cursor.moveToFirst()) {
            do {
                String Depense = cursor.getString(0);
                set_depenses.add(Depense);
            } while (cursor.moveToNext());
        }

        List<String> membres = new ArrayList<String>();
        RadarData radarData = new RadarData();
        for (String membre : set_membres){
            List<Integer> Depense_Value = new ArrayList<Integer>();
            ArrayList<RadarEntry> depensesformembres = new ArrayList<>();
            for (String DepType : set_depenses) {
                Cursor cursor1 = myDb.getValueByNomDepenseAndId(DepType,membre);

                int sum = 0;
                if (cursor1.moveToFirst()) {
                    do {
                        String depValeur = cursor1.getString(2);
                        sum = sum + Integer.parseInt(depValeur);
                    } while (cursor1.moveToNext());
                }

                Depense_Value.add(sum);
                depensesformembres.add(new RadarEntry(sum));
            }
            Cursor cursor1 = myDb.getMembreName(membre);
            cursor1.moveToFirst();
            String membrename = cursor1.getString(1);
            membres.add(membrename);
            RadarDataSet radarDataSetFormembresdepenses = new RadarDataSet(depensesformembres,membrename);
            Integer no = Integer.parseInt(membre);
            radarDataSetFormembresdepenses.setColor(COLORS[(no-1)%14]);
            radarDataSetFormembresdepenses.setValueTextColor(COLORS[(no-1)%14]);
            radarDataSetFormembresdepenses.setLineWidth(1f);
            radarDataSetFormembresdepenses.setValueTextSize(0);
            radarDataSetFormembresdepenses.setFillColor(COLORS[(no-1)%14]);
            radarDataSetFormembresdepenses.setDrawFilled(true);
            radarData.addDataSet(radarDataSetFormembresdepenses);

        }
        String[] labels = set_depenses.toArray(new String[0]);
        XAxis xAxis = radarChart.getXAxis();
        YAxis yAxis = radarChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);

        yAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.setData(radarData);
        radarChart.getDescription().setEnabled(false);


    }

}
