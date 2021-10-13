package com.samijaafar.gestion_budgetaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GrpDep.db";
    public static final String TABLE1_NAME = "Membres";
    public static final String COL1_1 = "MembreID";
    public static final String COL1_2 = "MembreNom";
    public static final String COL1_3 = "Titre";
    public static final String TABLE2_NAME = "Depenses";
    public static final String COL2_1 = "DepenseID";
    public static final String COL2_2 = "DepenseType";
    public static final String COL2_3 = "Valeur";
    public static final String COL2_4 = "MembreID";
    public static final String COL2_5 = "DateDepense";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (MembreID INTEGER PRIMARY KEY AUTOINCREMENT, MembreNom TEXT NOT NULL, Titre TEXT )");
        db.execSQL("create table "+ TABLE2_NAME + "( DepenseID INTEGER PRIMARY KEY AUTOINCREMENT, DepenseType TEXT NOT NULL , Valeur INTEGER NOT NULL , MembreID INTEGER NOT NULL,DateDepense DATE, FOREIGN KEY(MembreID) REFERENCES Membres(MembreID) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertDataTb1(String MembreNom,String Titre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_2,MembreNom);
        contentValues.put(COL1_3,Titre);
        long res = db.insert(TABLE1_NAME,null,contentValues);
        if(res==-1)
            return false;
        else
            return true;
    }
    public boolean insertDataTb2(String DepenseType,String Valeur,String MembreID,String DateDepense){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_2,DepenseType);
        contentValues.put(COL2_3,Valeur);
        contentValues.put(COL2_4,MembreID);
        contentValues.put(COL2_5,DateDepense);
        long res = db.insert(TABLE2_NAME,null,contentValues);
        if(res==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME,null);
        return res;
    }
    public Cursor getMembreName(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME+" where MembreID = "+"'"+Id+"'",null);
        return res;
    }
    public Cursor getAllDepense() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE2_NAME, null);
        return res;
    }
    public Cursor getDepensebyId(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME+" where MembreID = "+"'"+Id+"'",null);
        return res;
    }
    public Cursor getDepensebyIdandDate(String Id, String Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME+" where (MembreID = "+"'"+Id+"'"+" and DateDepense = "+"'"+Date+"'"+")",null);
        return res;
    }
    public Cursor getDepenseType() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DepenseType from "+TABLE2_NAME,null);
        return res;
    }
    public Cursor getDepenseTypeByNom(String Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME+" where DepenseType="+"'"+Type+"'",null);
        return res;
    }
    public Cursor getDateDepenseById(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Distinct DateDepense from "+TABLE2_NAME+" where MembreID = "+Id,null);
        return res;
    }
    public Cursor getValueByDepenseNomAndId(String DepNom,String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME+" where DepenseID="+"'"+DepNom+"' and MembreID="+"'"+Id+"'",null);
        return res;
    }
    public Cursor getValueByNomDepenseAndId(String DepNom,String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME+" where DepenseType="+"'"+DepNom+"' and MembreID="+"'"+Id+"'",null);
        return res;
    }


    public boolean updateByIdAndDepense(String DepNom,String Id,String Valeur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_3,Valeur);
        long res = db.update(TABLE2_NAME,contentValues,"DepenseID='"+DepNom+"' and MembreID='"+Id+"'",null);
        if(res==-1)
            return false;
        else
            return true;
    }
    public boolean deleteByIdAndDepense(String DepNom,String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long res = db.delete(TABLE2_NAME,"DepenseID='"+DepNom+"' and MembreID='"+Id+"'",null);
        if(res==-1)
            return false;
        else
            return true;
    }

    public boolean dropDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        File file = new File(db.getPath());
        if(SQLiteDatabase.deleteDatabase(file))
            return true;
        else
            return false;
    }
}

