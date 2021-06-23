package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Nos lleva a la pantalla de Productos
    public void irProductos(View view){
        Intent productos = new Intent(this, Productos.class);
        startActivity(productos);
    }

    //Nos lleva a la pantalla de Marcas
    public void irMarcas(View view){
        Intent marcas = new Intent(this, Marcas.class);
        startActivity(marcas);
    }

    //Nos lleva a la pantalla de Categorias
    public void irCategorias(View view){
        Intent categorias = new Intent(this, Categorias.class);
        startActivity(categorias);
    }

}