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

public class Categorias extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        //Vinculamos una las variables con los elementos en XML
        listView = findViewById(R.id.listaCategorias);
        cargarCategorias(); //Carga las Categorias en el Listview
    }

    public void cargarCategorias(){
        ArrayList<String> listaCategorias = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null,1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("SELECT nombre FROM categorias",null);
        if(cursor.moveToFirst()){
            do{
                String registro = cursor.getString(0);
                listaCategorias.add(registro);
            }while(cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCategorias);
        listView.setAdapter(adapter);

        BaseDeDatos.close();
        cursor.close();
    }

    public void regresarMenu(View view){ //Nos regresa a la Activity Principal
        Intent menuPrincipal = new Intent(this, MainActivity.class);
        startActivity(menuPrincipal);
    }
}