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

    public void irProductos(View view){
        Intent productos = new Intent(this, Productos.class);
        startActivity(productos);
    }

    public void irMarcas(View view){
        Intent marcas = new Intent(this, Marcas.class);
        startActivity(marcas);
    }

    public void irCategorias(View view){
        Intent categorias = new Intent(this, Categorias.class);
        startActivity(categorias);
    }

}