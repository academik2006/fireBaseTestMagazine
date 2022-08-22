package com.dima.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.dima.myfirebaseapp.Models.Category;
import com.dima.myfirebaseapp.Helpers.FoodListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodPage extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("Category");

        listView = findViewById(R.id.list_of_food);

        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Category> allFood = new ArrayList<>();
                for (DataSnapshot obj : snapshot.getChildren()){
                    Category category = obj.getValue(Category.class);
                    allFood.add (category);
                }

                FoodListAdapter adapter = new FoodListAdapter(FoodPage.this, R.layout.food_item_for_list,allFood);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodPage.this, "Нет интернета", Toast.LENGTH_LONG).show();

            }
        });



    }
}