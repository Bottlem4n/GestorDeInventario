package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar_Marca extends AppCompatActivity {

    private EditText nombreMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_marca);

        nombreMarca = findViewById(R.id.nombreMarca);
    }

    public void cancelarAgregar(View view){
        Intent marcas = new Intent(this, Marcas.class);
        startActivity(marcas);
    }

    public void guardarAgregar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        String nombre = nombreMarca.getText().toString();

        if(nombre.isEmpty()){
            Toast.makeText(this, "No dejes vacio el campo de nombre",Toast.LENGTH_SHORT).show();
        }
        else {

            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);

            BaseDeDatos.insert("marcas", null, registro);
            BaseDeDatos.close();

            Toast.makeText( this, "Producto Guardado", Toast.LENGTH_SHORT).show();
            limpiar();
            regresarMarcas();
        }
    }

    public void limpiar(){
        nombreMarca.setText("");
    }

    public void regresarMarcas(){
        Intent listaMarcas = new Intent(this, Marcas.class);
        startActivity(listaMarcas);
    }
}