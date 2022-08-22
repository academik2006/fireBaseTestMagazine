package com.dima.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dima.myfirebaseapp.Helpers.CartItemsAdapter;
import com.dima.myfirebaseapp.Models.Cart;
import com.dima.myfirebaseapp.Helpers.JsonHelper;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView shopping_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        shopping_list = findViewById(R.id.shopping_list);

        List<Cart> cartList = JsonHelper.importFromJson(this);

        if (cartList != null) {

            CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(CartActivity.this,R.layout.cart_item,cartList);
            shopping_list.setAdapter(cartItemsAdapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Не удалось подгрузить данные", Toast.LENGTH_SHORT).show();


    }
}