package com.dima.myfirebaseapp.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dima.myfirebaseapp.FoodDetail;
import com.dima.myfirebaseapp.Models.Cart;
import com.dima.myfirebaseapp.Models.Category;
import com.dima.myfirebaseapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class CartItemsAdapter extends ArrayAdapter<Cart> {


    private final LayoutInflater layoutInflater;
    private final List<Cart> cartList;
    private final int layoutListRow;


    public CartItemsAdapter(@NonNull Context context, int resource, @NonNull List<Cart> objects) {
        super(context, resource, objects);
        cartList = objects;
        layoutListRow=resource;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = layoutInflater.inflate(layoutListRow,parent,false);
        Cart cart = cartList.get(position);

        if (cart != null) {
            TextView productName = convertView.findViewById(R.id.product_name);
            TextView amount = convertView.findViewById(R.id.amount);


            if (amount != null) amount.setText(String.valueOf(cart.getAmount()));

            if (productName != null) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table = database.getReference("Category");

                table.child(String.valueOf(cart.getCategoryID())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Category category = snapshot.getValue(Category.class);
                        productName.setText(category.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

        }

        return convertView;

    }


}
