package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AgregarProducto extends AppCompatActivity {

    private Spinner sMarcas, sCategorias;
    private int posicionMarca, posicionCategoria;
    private EditText nombreProducto, detalleProducto, cantidadProducto, precioProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        //Vinculamos una las variables con los elementos en XML
        nombreProducto = findViewById(R.id.nombreProducto);
        detalleProducto = findViewById(R.id.detallesProducto);
        cantidadProducto = findViewById(R.id.cantidadProducto);
        precioProducto = findViewById(R.id.precioProducto);

        sMarcas = findViewById(R.id.spinnerMarcas);

        //Agregamos un evento que se dispara cuando se selecciona un elemento del spinner de Marcas
        sMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionMarca = parent.getSelectedItemPosition() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sCategorias = findViewById(R.id.spinnerCategorias);

        //Agregamos un evento que se dispara cuando se selecciona un elemento del spinner de Categorias
        sCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCategoria = parent.getSelectedItemPosition() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cargarMarcas(); //Carga las marcas en su Spinner
        cargarCategorias(); //Carga las categorias en su Spinner
    }

    public void cargarMarcas(){
        ArrayList<String> listaMarcas = new ArrayList<>();
        listaMarcas.add("Selecciona");

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursorMarcas = BaseDeDatos.rawQuery("SELECT clave_marca,nombre FROM marcas",null);

        if(cursorMarcas.moveToFirst()){
            do{
                String reg = cursorMarcas.getString(1);
                listaMarcas.add(reg);
            }while(cursorMarcas.moveToNext());
        }

        BaseDeDatos.close();
        cursorMarcas.close();

        ArrayAdapter <String> adapterMarcas = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaMarcas);
        sMarcas.setAdapter(adapterMarcas);
    }

    public void cargarCategorias() {
        ArrayList<String> listaCategorias = new ArrayList<>();
        listaCategorias.add("Selecciona");

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        Cursor cursorCategorias = BaseDeDatos.rawQuery("SELECT clave_categoria,nombre FROM categorias", null);

        if (cursorCategorias.moveToFirst()) {
            do {
                String reg = cursorCategorias.getString(1);
                listaCategorias.add(reg);
            } while (cursorCategorias.moveToNext());
        }

        BaseDeDatos.close();
        cursorCategorias.close();

        ArrayAdapter<String> adapterMarcas = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaCategorias);
        sCategorias.setAdapter(adapterMarcas);
    }

    public void cancelarAgregar(View view){
        Intent productos = new Intent(this, Productos.class);
        startActivity(productos);
    }

    public void guardarAgregar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        String nombre = nombreProducto.getText().toString();
        String detalle = detalleProducto.getText().toString();
        String cantidad = cantidadProducto.getText().toString();
        String precio = precioProducto.getText().toString();
        int marca = posicionMarca;
        int categoria = posicionCategoria;

        if(nombre.isEmpty()){
            Toast.makeText(this, "No dejes vacio el campo de nombre",Toast.LENGTH_SHORT).show();
        }
        else if (detalle.isEmpty()){
            Toast.makeText(this, "No dejes vacio el campo de detalles",Toast.LENGTH_SHORT).show();
        }
        else if (cantidad.isEmpty()){
            Toast.makeText(this, "No dejes vacio el campo de cantidad",Toast.LENGTH_SHORT).show();
        }
        else if (precio.isEmpty()){
            Toast.makeText(this, "No dejes vacio el campo de precio",Toast.LENGTH_SHORT).show();
        }
        else if (marca == 0){
            Toast.makeText(this, "Selecciona una Marca",Toast.LENGTH_SHORT).show();
        }
        else if (categoria == 0){
            Toast.makeText(this, "Selecciona una Categoria",Toast.LENGTH_SHORT).show();
        }
        else {
            int cant = Integer.parseInt(cantidad);
            float pre = Float.parseFloat(precio);

            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("detalle", detalle);
            registro.put("cantidad", cant);
            registro.put("precio", pre);
            registro.put("clave_marca", marca);
            registro.put("clave_categoria", categoria);


            BaseDeDatos.insert("productos", null, registro);
            BaseDeDatos.close();

            Toast.makeText( this, "Producto Guardado", Toast.LENGTH_SHORT).show();
            limpiar();
            regresarProductos();
        }
    }

    public void limpiar(){ //Limpia los Campos
        nombreProducto.setText("");
        detalleProducto.setText("");
        cantidadProducto.setText("");
        precioProducto.setText("");

        sMarcas.setSelection(0);
        sCategorias.setSelection(0);
    }

    public void regresarProductos(){ //Nos regresa a la Activity Productos
        Intent listaProductos = new Intent(this, Productos.class);
        startActivity(listaProductos);
    }


}