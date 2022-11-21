package com.example.moyaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import User.User;
import User.UserManager;

public class Principal extends AppCompatActivity {

    public TextView textView;
    public Button magicButton;
    public ImageView tv;
    int [] photos = {R.drawable.city1, R.drawable.city2, R.drawable.city3, R.drawable.city4, R.drawable.city5, R.drawable.city6};

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
        tv = findViewById(R.id.imageView);
        select();
        magicButton = findViewById(R.id.magicButton);
        magicButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select();
                    }
                });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select();
            }
        });
    }

    public void select(){
        Random ran = new Random();
        int i = ran.nextInt(photos.length);
        tv.setImageResource(photos[i]);
    }
}