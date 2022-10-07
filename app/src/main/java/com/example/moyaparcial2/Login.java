package com.example.moyaparcial2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private String name;

    private EditText inputUsuario;
    private EditText inputPass;

    private Button linkRegister;
    private Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        inputUsuario = findViewById(R.id.inputUsuario);
        inputPass = findViewById(R.id.inputPass);
        linkRegister = findViewById(R.id.buttonRegister);
        ingresar = findViewById(R.id.buttonIngresar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Leer registro de JSON
                //Comprobar usuario y pass
                Toast.makeText(getApplicationContext(),"Ingresando", Toast.LENGTH_LONG).show();
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
}