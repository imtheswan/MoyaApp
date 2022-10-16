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

    private EditText inputUsuario;
    private EditText inputPass;
    private Button linkRegister;
    private Button ingresar;

    private User user = new User();

    FileManager fileManager = new FileManager();
    JSONmanager jsonManager = new JSONmanager();
    UserManager userManager = new UserManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        inputUsuario = findViewById(R.id.inputUsuario);
        inputPass = findViewById(R.id.editTextPass);
        linkRegister = findViewById(R.id.buttonRegister);
        ingresar = findViewById(R.id.buttonIngresar);

        //verificar que haya un archivo de registro

        Boolean fileExists = fileManager.accessFile(getDataDir(),"RegistroMoyaApp.json");

        if(fileExists && fileManager.existence){
            Log.d("Estado", "Ya hay archivo registro");
        } else if(fileExists == null){
            Toast.makeText(getApplicationContext(), "No es posible crear registro", Toast.LENGTH_SHORT);
        }
        else{ //
            Log.d("Estado", "No hay archivo registro");
            User testUser = new User("Fulano Prueba");
            userManager.addUser(testUser);
            String json = jsonManager.getJSON(userManager);
            Log.d("Estado", "JSON: " + json);
            FileManager newFile = new FileManager();
            Boolean newFileExists = newFile.accessFile(getDataDir(), "RegistroMoyaApp.json");
            Log.d("Estado", "newfile" + newFileExists.toString());
            newFile.writePlainText(json);
            Toast.makeText(getApplicationContext(), "No hay ningun usuario registrado, regístrese", Toast.LENGTH_SHORT).show();
            hitAndRun(Register.class);
        }

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if(verifyData()){
                    String registroJSON = fileManager.readPlainText();
                    userManager = (UserManager) jsonManager.getObject(registroJSON, UserManager.class);
                    boolean auth = userManager.authenticateUser(user.getEmail(), user.getPass());
                    if(auth) {
                        Toast.makeText(getApplicationContext(), "Usuario valido", Toast.LENGTH_SHORT).show();
                        try{
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Login.this, Principal.class);
                                    Login.this.startActivity(intent);
                                    Login.this.finish();
                                }
                            });
                        } catch(Exception e){
                            Log.d("Estado", e.getStackTrace().toString());
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAndRun(Register.class);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    public void hitAndRun(Class activity){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Login.this, activity);
                Login.this.startActivity(intent);
                Login.this.finish();
            }
        });
    }

    public void getData(){
        String email = inputUsuario.getText().toString().toLowerCase();
        String pass = inputUsuario.getText().toString();
        user.setEmail(email);
        user.setPass(pass);
    }

    public boolean verifyData(){
        return true;
    }
}