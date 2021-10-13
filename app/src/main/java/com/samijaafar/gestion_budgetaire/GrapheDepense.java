package com.samijaafar.gestion_budgetaire;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class GrapheDepense extends AppCompatActivity {
    public static final int[] COLORS = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"), Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)
    };

    DatabaseHelper myDb;
    Cursor initial_cursor,cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphe_depense);
        myDb = new DatabaseHelper(getApplicationContext());
        initial_cursor = myDb.getDepenseType();
        Set<String> set = new HashSet<String>();
        if(initial_cursor.moveToFirst()){
            do{
                String DepType = initial_cursor.getString(0);
                set.add(DepType);
            }while (initial_cursor.moveToNext());
        }
        System.out.println(set);
        List<String> Depense_Type = new ArrayList<String>();
        List<Integer> Depense_Value = new ArrayList<Integer>();
        for(String DepType : set){
            Cursor cursor = myDb.getDepenseTypeByNom(DepType);
            Depense_Type.add(DepType);
            int sum = 0;
            if(cursor.moveToFirst()){
                do{
                    String depValeur = cursor.getString(2);
                    sum = sum + Integer.parseInt(depValeur);
                }while (cursor.moveToNext());
            }
            Depense_Value.add(sum);
        }
        String [] DepenseType_Arr = new String[Depense_Type.size()];
        DepenseType_Arr = Depense_Type.toArray(DepenseType_Arr);
        Integer [] DepenseValeur_Arr = new Integer[Depense_Value.size()];
        DepenseValeur_Arr = Depense_Value.toArray(DepenseValeur_Arr);
        List<PieEntry> pieEntries = new ArrayList<PieEntry>();
        for(int i=0;i<DepenseType_Arr.length;i++){
            pieEntries.add(new PieEntry(DepenseValeur_Arr[i],DepenseType_Arr[i]));
        }
        cursor = myDb.getAllData();
        cursor.moveToFirst();
        String titre = cursor.getString(2);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Valeur en Dirhams");
        pieDataSet.setColors(COLORS);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);

        PieChart pieChart = (PieChart)findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        pieChart.animateY(1000,Easing.EaseInOutCubic);
        pieChart.invalidate();
    }
}
