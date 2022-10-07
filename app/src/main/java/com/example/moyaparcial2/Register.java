package com.example.moyaparcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import Filemanager.Filemanager;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Filemanager filemanager = new Filemanager();
        Boolean status = filemanager.accesFile(getDataDir(), "test3.txt");
        Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
        filemanager.writePlainText("WEBOS");
        String texto = filemanager.readPlainText();
        Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();

    }
}