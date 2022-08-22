package com.dima.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dima.myfirebaseapp.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {


    private Button buttonEnter;
    private EditText editTextPhone, editTextPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonEnter = findViewById(R.id.buttonEnter);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("User");

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(editTextPhone.getText().toString()).exists()){
                            User user = snapshot.child(editTextPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editTextPassword.getText().toString())){

                                setDefault("phone", editTextPhone.getText().toString(),SignIn.this);
                                setDefault("name", user.getName(),SignIn.this);

                                Toast.makeText(SignIn.this, "Успешно авторизован", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, FoodPage.class);
                                startActivity(intent);
                            }
                            else Toast.makeText(SignIn.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                            
                        }
                        else Toast.makeText(SignIn.this, "Такого пользователя нет", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SignIn.this, "Нет интернета", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }


    public static void setDefault (String key, String value, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDefault (String key, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,null);

    }




}