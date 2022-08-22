package com.dima.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonReg, buttonAvtor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String active_user = SignIn.getDefault("phone",MainActivity.this);

        if (active_user != null) {

            Intent intent = new Intent(this, FoodPage.class);
            startActivity(intent);
        }


        buttonReg = findViewById(R.id.buttonReg);
        buttonAvtor = findViewById(R.id.buttonAvtor);

        buttonAvtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);

            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);

            }
        });



    }


}