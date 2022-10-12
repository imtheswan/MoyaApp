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

import java.io.File;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import User.User;
import User.UserManager;

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
        inputPass = findViewById(R.id.inputPassR);
        linkRegister = findViewById(R.id.buttonRegister);
        ingresar = findViewById(R.id.buttonIngresar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAndRun();
            }
        });

        //verificar que haya un archivo de registro

        FileManager fileManager = new FileManager();

        Boolean fileExists = fileManager.accessFile(getDataDir(),"RegistroMoyaApp.json");

        if(!fileExists && fileManager.existence){

        } else if(fileExists == null){
            Log.d("TMY", "Ya hay archivo registro");
            Toast.makeText(getApplicationContext(), "No es posible crear registro", Toast.LENGTH_SHORT);
        }
        else{ //
            Log.d("TMY", "No hay archivo registro");
            UserManager userManager = new UserManager();
            User testUser = new User("Fulano Prueba");

            JSONmanager jsonManager = new JSONmanager();
            String json = jsonManager.getJSON(testUser);
            jsonManager.getJSON(testUser);
            Log.d("TMY", "JSON: " + json);
            FileManager newFile = new FileManager();
            Boolean newFileExists = newFile.accessFile(getDataDir(), "RegistroMoyaApp.json");
            Log.d("TMY", "newfile" + newFileExists.toString());
            newFile.writePlainText(json);
            Toast.makeText(getApplicationContext(), "No hay ningun usuario registrado, regístrese", Toast.LENGTH_SHORT).show();
            hitAndRun();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    public void hitAndRun(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Login.this, Register.class);
                Login.this.startActivity(intent);
                Login.this.finish();
            }
        });
    }

    public void getData(){
        String name = inputUsuario.getText().toString();
        String pass = inputUsuario.getText().toString();
    }

    public Boolean verifyData(){
        return null;
    }
}