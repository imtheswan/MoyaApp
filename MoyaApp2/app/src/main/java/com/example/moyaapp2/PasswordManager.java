package com.example.moyaapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import Password.Password;
import Password.PassManager;

public class PasswordManager extends AppCompatActivity {

    PassManager universalPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);
        universalPass = accessPasswords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,  menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId() ) {
            case R.id.nuevoId:
                Password password = new Password("passPrueba", "qwerty");
                if(universalPass.addPassword(password)){
                    Toast.makeText(getApplicationContext(), "Contraseña agregada", Toast.LENGTH_SHORT).show();
                    savePasswords(universalPass);
                }
                else
                    Toast.makeText(getApplicationContext(), "Ya hay una contraseña con ese nombre", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private PassManager accessPasswords(){
        FileManager fileManager = new FileManager();
        JSONmanager jsonMan = new JSONmanager();
        PassManager passManager;

        boolean existence = fileManager.accessFile(getDataDir(), "passAdministration.json");
        if(existence){
            String json = fileManager.readByteStream();
            passManager = (PassManager) jsonMan.getObject(json, PassManager.class);
        } else{
            passManager = new PassManager();
            String json = jsonMan.getJSON(passManager);
            fileManager.writeByteStream(json);
        }
        return passManager;
    }

    private void savePasswords(PassManager passManager){
        FileManager fileManager = new FileManager();
        JSONmanager jsonMan = new JSONmanager();

        fileManager.accessFile(getDataDir(), "passAdministration.json");
        String json = jsonMan.getJSON(passManager);
        fileManager.writeByteStream(json);
    }

}