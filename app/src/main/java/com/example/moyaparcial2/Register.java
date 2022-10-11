package com.example.moyaparcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import User.User;
import User.UserManager;

public class Register extends AppCompatActivity {

    private EditText inputName;
    private EditText inputPass;
    private EditText inputCorreo;
    private EditText inputEdad;

    private Button submit;
    private Button linkLogin;

    private FileManager fm =  new FileManager();

    private User user = new User();

    private JSONmanager jsonm = new JSONmanager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = findViewById(R.id.inputName);
        inputCorreo = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPassR);
        inputEdad = findViewById(R.id.inputAge);

        submit = findViewById(R.id.submitButton);
        linkLogin = findViewById(R.id.loginLinkButton);

        fm.accessFile(getDataDir(), "RegistroTest1.txt");

        Toast.makeText(getApplicationContext(), getDataDir().toString(), Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if(writeData()){
                    hitAndRun();
                }
            }
        });

        linkLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { hitAndRun(); }
                }
        );
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
    }

    public void hitAndRun(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void test (){
        String text = fm.readPlainText();
        Boolean able = fm.writePlainText("Hola, mundo");
        Toast.makeText(getApplicationContext(), "TextInit: " + text, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "TextInit: " + able.toString(), Toast.LENGTH_LONG).show();
    }

    public void t(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void getData(){ //Obtiene los valores de las entradas
        String email = inputCorreo.getText().toString();
        String name = inputName.getText().toString();
        String pass = inputPass.getText().toString();
        //int edad = Integer.valueOf(inputEdad.getText().toString());
        int edad = 17;
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName("Mohamed");
        user.setPass(pass);
        user.setAge(edad);
        user.setGender(0);
    }

    public boolean verifyData(){ //Verifica que los campos sean correctos
        return true;
    }

    public boolean writeData(){
        if(verifyData()){
            boolean succes = false;

            FileManager userRegistry = new FileManager();
            UserManager usMG;
            JSONmanager jsonManager = new JSONmanager();

            Boolean creationState = userRegistry.accessFile(getDataDir(), "TablaRegistro.txt");
            Log.d("Estado" ,"AccesFile "+creationState.toString());
            if(creationState) {
                String registro = userRegistry.readPlainText();
                usMG = (UserManager) jsonManager.getObject(registro, UserManager.class);
                User newUser = new User();
                newUser.setFirstName("Juan Pablo");
                Log.d("Estado", usMG.showUsers());
            }
                /*if (usMG.allUsers.size() <= 0){
                    t("Objeto vacio, creando nuevo");
                    usMG = new UserManager();
                } else{
                    t("Objeto no vacio");
                }
                usMG.addUser(user);
                String newJson = jsonManager.getJSON(usMG);
                Log.d("Eroor",newJson);
                succes = userRegistry.writePlainText(newJson);
                t("Estado de creacion de usuario " + succes);
                return succes;
            }
            return false;
        }else{
            Toast.makeText(getApplicationContext(), "Imposible registrar", Toast.LENGTH_SHORT).show();
            return false;
        }*/
    }

}