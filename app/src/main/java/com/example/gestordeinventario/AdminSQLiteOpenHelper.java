package com.example.gestordeinventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory,version);
    }

    public void onCreate(SQLiteDatabase BaseDeDatos){
        BaseDeDatos.execSQL("CREATE TABLE categoria (clave_categoria INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL, descripcion TEXT NOT NULL)");
        BaseDeDatos.execSQL("CREATE TABLE marca (clave_marca INTEGER PRIMARY KEY UNIQUE NOT NULL,nombre TEXT NOT NULL)");

    }

    public void onOpen (SQLiteDatabase BaseDeDatos){
        BaseDeDatos.execSQL("PRAGMA foreign_kets=ON");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
