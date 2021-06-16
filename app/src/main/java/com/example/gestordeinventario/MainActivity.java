package com.example.gestordeinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargar();
    }

    public void cargar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "inventario",null,1);
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
    }
}