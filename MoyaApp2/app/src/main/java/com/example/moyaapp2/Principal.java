package com.example.moyaapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import DesUtil.DesUtil;
import Filemanager.FileManager;
import JSONmanager.JSONmanager;
import Password.Password;
import User.User;
import User.UserManager;

public class Principal extends AppCompatActivity {

    public Button passButton;
    private User user;
    public TextView textView;
    public Button magicButton;
    public ImageView tv;
    int [] photos = {R.drawable.city1, R.drawable.city2, R.drawable.city3, R.drawable.city4, R.drawable.city5, R.drawable.city6};

    private static final String key = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "Hola mundo";
    private String testDecifrado;
    public DesUtil desUtil = new DesUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        desUtil.addStringKeyBase64(key);
        String testCifrado = desUtil.cifrar(testClaro);
        Log.d("CC", "onCreate: Cifrado> " + testCifrado);
        testDecifrado = desUtil.desCifrar(testCifrado);
        Log.d("CC", "onCreate: DESCifrado> " + testDecifrado);
        // Display User Info
        textView = findViewById(R.id.text6);
        Intent intent = getIntent();
        String correo = intent.getStringExtra("userkey");

        if (correo != null){
            UserManager userManager = new UserManager();
            FileManager fm = new FileManager();
            JSONmanager json = new JSONmanager();

            fm.accessFile(getDataDir(), "RegistroMoyaApp.json");
            String registroJSON = fm.readByteStream();
            registroJSON = desUtil.desCifrar(registroJSON);
            Log.d("Estado", "JSON LOGIN " + registroJSON);
            userManager = (UserManager) json.getObject(registroJSON, UserManager.class);
            user = userManager.getUser(correo);
            textView.setText(user.show());
        }

        // Random Gallery
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

        passButton = findViewById(R.id.button);
        passButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hitAndRun(PasswordManager.class);
                    }
                }
        );

    }

    public void select(){
        Random ran = new Random();
        int i = ran.nextInt(photos.length);
        tv.setImageResource(photos[i]);
    }

    public void hitAndRun(Class activity){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Principal.this, activity);
                intent.putExtra("userkey", user.getEmail());
                Principal.this.startActivity(intent);
            }
        });
    }


}