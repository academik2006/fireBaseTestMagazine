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
import com.dima.myfirebaseapp.Models.Category;
import com.dima.myfirebaseapp.R;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Category> {

    private final LayoutInflater layoutInflater;
    private final List<Category> categories;
    private final int layoutListRow;

    private Context context;

    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        categories = objects;
        layoutListRow=resource;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        convertView = layoutInflater.inflate(layoutListRow,parent,false);
        Category category = categories.get(position);

        if (category != null) {

            TextView foodName = convertView.findViewById(R.id.food_name);
            ImageView photo = convertView.findViewById(R.id.mainfoto);

            if (foodName != null) foodName.setText(category.getName());

            if (photo != null) {
                int id = getContext().getResources().getIdentifier("drawable/" + category.getImage(), null, getContext().getPackageName());
                photo.setImageResource(id);

                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FoodDetail.ID = position + 1;
                        context.startActivity(new Intent(context,FoodDetail.class));

                    }
                });

            }

        }

        return convertView;

    }



}
