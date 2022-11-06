package com.example.moyaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import User.User;
import User.UserManager;

public class Principal extends AppCompatActivity {

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        textView = findViewById(R.id.text6);

        Intent intent = getIntent();
        String correo = intent.getStringExtra("userkey");

        if (correo != null){
            UserManager userManager = new UserManager();
            FileManager fm = new FileManager();
            JSONmanager json = new JSONmanager();

            fm.accessFile(getDataDir(), "RegistroMoyaApp.json");
            String registroJSON = fm.readByteStream();
            Log.d("Estado", "JSON LOGIN " + registroJSON);
            userManager = (UserManager) json.getObject(registroJSON, UserManager.class);
            User user = userManager.getUser(correo);
            textView.setText(user.show());
        }
    }
}