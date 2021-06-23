package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Productos extends AppCompatActivity {

    private ListView listView;
    private Object posicionItem;
    private ArrayList<String> listaClavesProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        //Vinculamos una las variables con los elementos en XML
        listView = findViewById(R.id.listaProductos);

        //Agregamos un evento que se dispara cuando se da click en un elemento de la lista
        listView.setOnItemClickListener((parent, view, position, id) -> {

            posicionItem = parent.getItemIdAtPosition(position); //Obtenemos la posicion del elemento clickeado
            int posItem = Integer.parseInt(posicionItem.toString()); //Se convierte a entero la posicion
            String claveIntercambio = listaClavesProductos.get(posItem); //Objetemos la clave del producto por medio de la posicion el elemento clickeado

            Intent mostrarDetalles = new Intent(getApplicationContext(), MostrarDetalles.class);
            mostrarDetalles.putExtra("clave_producto",claveIntercambio); //Le enviamos a la siguiente Activity la clave del producto
            startActivity(mostrarDetalles); //Nos movemos a la Activity MostarDetalles

            //Toast.makeText(parent.getContext(), "Posicion " + claveIntercambio ,Toast.LENGTH_SHORT).show();
        });
        cargarProductos(); //Cargamos lo productos en el ListView

    }

    public void cargarProductos(){
        ArrayList<String> listaProductos = new ArrayList<>();

        //Conectamos con la Base de Datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null,1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursor = BaseDeDatos.rawQuery("SELECT clave_producto, nombre FROM productos",null); //Hacemos una consulta que nos trae todos los elementos de la tabla productos
        if(cursor.moveToFirst()){ //Recorremos el cursor para obtener la clave y nombre de cada producto
            do{
                String claves = cursor.getString(0);
                listaClavesProductos.add(claves);//Se guarda la clave del producto en un arreglo

                String registro = cursor.getString(1);
                listaProductos.add(registro);//Se guarda el nombre del prodcuto en un arreglo
            }while(cursor.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProductos);
        listView.setAdapter(adapter);//Se pasa el arreglo a un adaptador para que lo muestre en el ListView

        BaseDeDatos.close();//Cerramos la conexion con la BD
        cursor.close();//Cerramos el Cursor
    }

    public void regresarMenu(View view){ //Nos regresa a la pantalla principal
        Intent menuPrincipal = new Intent(this, MainActivity.class);
        startActivity(menuPrincipal);
    }

    public void irAgregarProducto(View view){ //Nos lleva a la pantalla para agregar un nuevo producto
        Intent agregarProducto = new Intent(this, AgregarProducto.class);
        startActivity(agregarProducto);
    }
}