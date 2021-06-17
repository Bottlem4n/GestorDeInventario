package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.PrivateKey;
import java.util.ArrayList;

public class Productos extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        listView = findViewById(R.id.listaProductos);
        cargarProductos();

    }

    public void cargarProductos(){
        ArrayList<String> listaProductos = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProductos);
        listView.setAdapter(adapter);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null,1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("SELECT nombre FROM productos",null);
        if(cursor.moveToFirst()){
            do{
                String registro = cursor.getString(0);
                listaProductos.add(registro);
            }while(cursor.moveToNext());
        }

        cursor.close();
    }

    public void regresarMenu(View view){
        Intent menuPrincipal = new Intent(this, MainActivity.class);
        startActivity(menuPrincipal);
    }

    public void irAgregarProducto(View view){
        Intent agregarProducto = new Intent(this, AgregarProducto.class);
        startActivity(agregarProducto);
    }
}