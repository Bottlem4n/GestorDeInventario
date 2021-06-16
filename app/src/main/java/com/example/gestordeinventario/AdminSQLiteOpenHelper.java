package com.example.gestordeinventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory,version);
    }

    public void onCreate(SQLiteDatabase BaseDeDatos){
        BaseDeDatos.execSQL("CREATE TABLE categorias (clave_categoria INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL, descripcion TEXT NOT NULL)");
        BaseDeDatos.execSQL("CREATE TABLE marcas (clave_marca INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL)");
        BaseDeDatos.execSQL("CREATE TABLE productos (clave_producto INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, detalle TEXT NOT NULL, cantidad integer Not NULL, precio real NOT NULL, clave_marca integer, clave_categoria integer, FOREIGN KEY (clave_marca) REFERENCES marcas (clave_marca), FOREIGN KEY (clave_categoria) REFERENCES categorias (clave_categoria))");
    }

    public void onOpen (SQLiteDatabase BaseDeDatos){
        BaseDeDatos.execSQL("PRAGMA foreign_keys=ON");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
