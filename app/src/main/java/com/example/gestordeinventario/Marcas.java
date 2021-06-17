package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Marcas extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas);

        listView = findViewById(R.id.listaMarcas);
        cargarMarcas();
    }

    public void cargarMarcas(){
        ArrayList<String> listaMarcas = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null,1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("SELECT nombre FROM marcas",null);
        if(cursor.moveToFirst()){
            do{
                String registro = cursor.getString(0);
                listaMarcas.add(registro);
            }while(cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMarcas);
        listView.setAdapter(adapter);


        cursor.close();
    }

    public void regresarMenu(View view){
        Intent menuPrincipal = new Intent(this, MainActivity.class);
        startActivity(menuPrincipal);
    }

    public void irAgregarMarca(View view){
        Intent agregarMarca = new Intent(this, Agregar_Marca.class);
        startActivity(agregarMarca);
    }
}