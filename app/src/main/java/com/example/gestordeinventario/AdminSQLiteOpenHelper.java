package com.example.gestordeinventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory,version);
    }

    public void onCreate(SQLiteDatabase BaseDeDatos){
        //Creamos las tablas que vamos a ocupar
        BaseDeDatos.execSQL("CREATE TABLE categorias (clave_categoria INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL, descripcion TEXT NOT NULL)");
        BaseDeDatos.execSQL("CREATE TABLE marcas (clave_marca INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL)");
        BaseDeDatos.execSQL("CREATE TABLE productos (clave_producto INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, detalle TEXT NOT NULL, cantidad integer Not NULL, precio real NOT NULL, clave_marca integer, clave_categoria integer, FOREIGN KEY (clave_marca) REFERENCES marcas (clave_marca), FOREIGN KEY (clave_categoria) REFERENCES categorias (clave_categoria))");

        //Introducimos registros iniciales a las tablas antes creadas para poder inicializar las primary key autoincrement
        BaseDeDatos.execSQL("INSERT INTO categorias(clave_categoria,nombre,descripcion) VALUES (1,'Bebidas','Refresco, Agua y Té')");
        BaseDeDatos.execSQL("INSERT INTO categorias(nombre,descripcion) VALUES ('Botanas','Papas,Cacahuates y Frutos Secos')");
        BaseDeDatos.execSQL("INSERT INTO categorias(nombre,descripcion) VALUES ('Lacteos','Leche,Huevos y Quesos')");
        BaseDeDatos.execSQL("INSERT INTO categorias(nombre,descripcion) VALUES ('Farmacia','Vitaminas y Cuidado Personal')");

        BaseDeDatos.execSQL("INSERT INTO marcas(clave_marca,nombre) VALUES (1,'Coca-Cola')");
        BaseDeDatos.execSQL("INSERT INTO marcas(nombre) VALUES ('Pepsico')");

        BaseDeDatos.execSQL("INSERT INTO productos(clave_producto,nombre, detalle,cantidad,precio,clave_marca,clave_categoria) VALUES (700,'Coca-Cola', '600ml',10,13.5,1,1)");

    }

    public void onOpen (SQLiteDatabase BaseDeDatos){
        //Activamos las foreign keys ya que en SQLite vienen desactivadas por defecto
        BaseDeDatos.execSQL("PRAGMA foreign_keys=ON");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
