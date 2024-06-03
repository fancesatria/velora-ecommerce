package com.uas.nb_official.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SPHelper {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;

    public SPHelper(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences("apiku", context.MODE_PRIVATE);
        this.editor = sp.edit();
        this.gson = new Gson();
    }

    public String getValue(String key){
        return sp.getString(key, "");
    }

    public void setValue(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getToken(){
        return getValue("token");
    }

    public void setToken(String token){
        editor.putString("token", token);
        editor.commit();
    }

    public void setUsername(String username){
        editor.putString("username", username);
        editor.commit();
    }

    public String getUsername(){
        return getValue("username");
    }

    public void setIdPengguna(int idpengguna){
        editor.putInt("idpengguna", idpengguna);
        editor.commit();
    }

    public int getIdPengguna(){
        return sp.getInt("idpengguna", 0);
    }

    public void setEmail(String email){
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail(){
        return getValue("email");
    }

    public void clearData(){
        editor.clear();
        editor.commit();
    }

    public void addToCart(String item) {
        List<String> cartItems = getCartItems();
        cartItems.add(item);
        String jsonCartItems = gson.toJson(cartItems);
        editor.putString("cart", jsonCartItems);
        editor.commit();
    }

    public void removeFromCart(String item) {
        List<String> cartItems = getCartItems();
        cartItems.remove(item);
        String jsonCartItems = gson.toJson(cartItems);
        editor.putString("cart", jsonCartItems);
        editor.commit();
    }

    public List<String> getCartItems() {
        String jsonCartItems = sp.getString("cart", null);
        if (jsonCartItems != null) {
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(jsonCartItems, type);
        }
        return new ArrayList<>();
    }


}
