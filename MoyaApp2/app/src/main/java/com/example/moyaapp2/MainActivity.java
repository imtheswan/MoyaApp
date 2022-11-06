package com.example.moyaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Crear objto tipo action bar, la barrita encima de la aplicacion
        ActionBar actionBar = getSupportActionBar();//Regresa el action bar de la actividad
        actionBar.hide();// La oculta
        Handler handler = new Handler(); //Crear nuevo handler
        //Handler permite correr runnables y mandar mensajes asociados a Threads
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class); //Literal
                // un intent es para saltar a activities e incluso otras aplicaciones
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish(); // Termina la actividad y no podremos volver a ella, podriamos hacerlo si no la terminaramos
            }
        }, 1000);
        //finish(); // Termina la actividad y no podremos volver a ella, podriamos hacerlo si no la terminaramos
    }
}