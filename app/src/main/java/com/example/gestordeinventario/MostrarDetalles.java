package com.example.gestordeinventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarDetalles extends AppCompatActivity {


    private int clave_producto;
    private EditText cve_producto;
    private Spinner sMarcas, sCategorias;
    private int posicionMarca, posicionCategoria;
    private EditText nombreProducto, detalleProducto, cantidadProducto, precioProducto;
    private String clave;
    private Button botonDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_detalles);

        Bundle mensaje = getIntent().getExtras();
        clave_producto = mensaje.getInt("clave_producto");
        //Toast.makeText(this, "Clave:  " + clave_producto ,Toast.LENGTH_SHORT).show();

        clave = String.valueOf(clave_producto);
        cve_producto = findViewById(R.id.claveProducto);
        cve_producto.setText(String.valueOf(clave_producto));
        nombreProducto = findViewById(R.id.nombreProducto);
        detalleProducto = findViewById(R.id.detallesProducto);
        cantidadProducto = findViewById(R.id.cantidadProducto);
        precioProducto = findViewById(R.id.precioProducto);

        sMarcas = findViewById(R.id.spinnerMarcas);

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

        sCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionCategoria = parent.getSelectedItemPosition() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cargarMarcas();
        cargarCategorias();
        cargarDetalle();

        botonDialogo = findViewById(R.id.botonEliminarProducto);
        botonDialogo.setOnClickListener(v -> {
            AlertDialog.Builder alerta = new AlertDialog.Builder(MostrarDetalles.this);
            alerta.setMessage("¿Deseas eliminar este producto?")
                    .setCancelable(false)
                    .setPositiveButton("Si", (dialog, which) -> eliminarProducto())
            .setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Confirmación de Eliminar");
            titulo.show();
        });

    }


    public void cargarDetalle(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM productos WHERE clave_producto="+ clave, null);
        if(fila.moveToFirst()){
            nombreProducto.setText(fila.getString(1));
            detalleProducto.setText(fila.getString(2));
            cantidadProducto.setText(fila.getString(3));
            precioProducto.setText(fila.getString(4));
            sMarcas.setSelection(fila.getInt(5));
            sCategorias.setSelection(fila.getInt(6));

        }

        fila.close();

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

        ArrayAdapter<String> adapterMarcas = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaMarcas);
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

    public void cancelarActualizar(View view){
        Intent productos = new Intent(this, Productos.class);
        startActivity(productos);
    }

    public void actualizarProducto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
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


            BaseDeDatos.update("productos", registro, "clave_producto="+clave ,null);
            BaseDeDatos.close();

            Toast.makeText( this, "Producto Actualizado", Toast.LENGTH_SHORT).show();
            regresarProductos();
        }


    }
    public void eliminarProducto(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"inventario",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String claveEliminar = clave;

        BaseDeDatos.delete("productos","clave_producto=?", new String[]{claveEliminar});
        BaseDeDatos.close();
        Toast.makeText(MostrarDetalles.this, "Eliminado",Toast.LENGTH_SHORT).show();
        regresarProductos();
    }

    public void regresarProductos(){
        Intent listaProductos = new Intent(this, Productos.class);
        startActivity(listaProductos);
    }
}