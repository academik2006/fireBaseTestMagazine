package com.dima.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Registration extends AppCompatActivity {

    private Button buttonReg;
    private EditText editTextPhone, editTextPassword, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        buttonReg = findViewById(R.id.buttonReg);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("User");


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(editTextPhone.getText().toString()).exists()){
                            Toast.makeText(Registration.this, "Такой пользователь у нас есть", Toast.LENGTH_LONG).show();
                        }
                        else {
                            User user = new User(editTextName.getText().toString(),editTextPassword.getText().toString());
                            table.child(editTextPhone.getText().toString()).setValue(user);
                            Toast.makeText(Registration.this, "Успешная регистрация нового пользователя", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Registration.this, "Нет интернета", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }
}