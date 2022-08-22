package com.dima.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dima.myfirebaseapp.Models.Cart;
import com.dima.myfirebaseapp.Models.Category;
import com.dima.myfirebaseapp.Helpers.JsonHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodDetail extends AppCompatActivity {


    public static int ID=0;

    private ImageView mainPhoto;
    private TextView food_main_name, price, food_full_name;
    private Button buttonGoToCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mainPhoto = findViewById(R.id.mainPhoto);

        food_main_name = findViewById(R.id.food_main_name);
        price = findViewById(R.id.price);
        food_full_name = findViewById(R.id.food_full_name);
        buttonGoToCard = findViewById(R.id.buttonGoToCard);

        buttonGoToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDetail.this, CartActivity.class));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("Category");

        table.child(String.valueOf(ID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Category category = snapshot.getValue(Category.class);
                food_main_name.setText(category.getName());
                int id = getApplicationContext().getResources().getIdentifier("drawable/" + category.getImage(), null, getApplicationContext().getPackageName());
                mainPhoto.setImageResource(id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FoodDetail.this, "Нет интернета", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void btnAddToCard (View view){

        List<Cart> cartList = JsonHelper.importFromJson(this);
        if (cartList==null){
            cartList = new ArrayList<>();
            cartList.add(new Cart(ID,100));
        } else {
            boolean isFound = false;
            for (Cart el: cartList) {
                if (el.getCategoryID() == ID) {
                    el.setAmount(el.getAmount() + 1);
                    isFound = true;
                }
            }

            if (!isFound) cartList.add(new Cart(ID,1));
        }

        boolean result = JsonHelper.exportToJson(this,cartList);
        if (result) {
            Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
            Button btnCart = (Button) view;
            btnCart.setText("Добавлено");
        }
        else Toast.makeText(this, "Не добавлено", Toast.LENGTH_SHORT).show();

        cartList = JsonHelper.importFromJson(this);
        for (Cart el: cartList) System.out.println(el);


    }
}