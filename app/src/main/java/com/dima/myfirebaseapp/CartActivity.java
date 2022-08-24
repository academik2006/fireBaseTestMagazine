package com.dima.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dima.myfirebaseapp.Helpers.CartItemsAdapter;
import com.dima.myfirebaseapp.Models.Cart;
import com.dima.myfirebaseapp.Helpers.JsonHelper;
import com.dima.myfirebaseapp.Models.Order;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView shopping_list;
    private Button make_order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        shopping_list = findViewById(R.id.shopping_list);
        make_order_button = findViewById(R.id.make_order_button);


        List<Cart> cartList = JsonHelper.importFromJson(this);

        if (cartList != null) {

            CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(CartActivity.this,R.layout.cart_item,cartList);
            shopping_list.setAdapter(cartItemsAdapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Не удалось подгрузить данные", Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("Order");

        make_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<Cart> cartList = JsonHelper.importFromJson(CartActivity.this);
                        if (cartList==null) {
                            Toast.makeText(CartActivity.this, "В корзине нет товаров", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String active_user = SignIn.getDefault("phone",CartActivity.this);
                        Order order = new Order(JsonHelper.createJsonString(cartList),active_user);

                        Long tslong = System.currentTimeMillis()/1000;
                        table.child(tslong.toString()).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                List<Cart> cartList = new ArrayList<>();
                                JsonHelper.exportToJson(CartActivity.this, cartList);
                                CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(CartActivity.this,R.layout.cart_item,cartList);
                                shopping_list.setAdapter(cartItemsAdapter);
                                Toast.makeText(CartActivity.this, "Заказ сформирован", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}