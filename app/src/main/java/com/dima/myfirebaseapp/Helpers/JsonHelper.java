package com.dima.myfirebaseapp.Helpers;

import android.content.Context;

import com.dima.myfirebaseapp.Models.Cart;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JsonHelper {

    private static final String FILE_NAME = "shopping_cart.json";

    public static boolean exportToJson (Context context, List<Cart> datalist) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setCartlist(datalist);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;


    }

    public static List<Cart> importFromJson(Context context){

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader,DataItems.class);

            return dataItems.getCartlist();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream !=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;


    }

    private static class DataItems {
        private List<Cart> cartlist;

        public List<Cart> getCartlist() {
            return cartlist;
        }

        public void setCartlist(List<Cart> cartlist) {
            this.cartlist = cartlist;
        }
    }


}
