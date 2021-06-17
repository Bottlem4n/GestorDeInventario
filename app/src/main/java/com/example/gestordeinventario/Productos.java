package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Productos extends AppCompatActivity {

    private ListView listView;
    private Object posicionItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        listView = findViewById(R.id.listaProductos);
        listView.setOnItemClickListener((parent, view, position, id) -> {

            posicionItem = parent.getItemIdAtPosition(position);
            int posItem = Integer.parseInt(posicionItem.toString());
            posItem +=700;

            Intent mostrarDetalles = new Intent(getApplicationContext(), MostrarDetalles.class);
            mostrarDetalles.putExtra("clave_producto",posItem);
            startActivity(mostrarDetalles);

            //Toast.makeText(parent.getContext(), "Posicion " + posItem ,Toast.LENGTH_SHORT).show();
        });
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