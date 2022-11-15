package com.example.moyaapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        int[] photos={R.drawable.city1, R.drawable.city2, R.drawable.city3, R.drawable.city4,
        R.drawable.city5, R.drawable.city6};

        ImageView image = (ImageView) findViewById(R.id.imageview1);

        Random ran=new Random();
        int i=ran.nextInt(photos.length);
        image.setImageResource(photos[i]);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int k=ran.nextInt(photos.length);
                image.setImageResource(photos[k]);
            }
        }
        );
    }
}