package com.uas.nb_official.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.uas.nb_official.Helper.API;
import com.uas.nb_official.Helper.Modul;
import com.uas.nb_official.Helper.SPHelper;
import com.uas.nb_official.Model.CartItem;
import com.uas.nb_official.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private CartUpdateListener cartUpdateListener;

    public CartAdapter(List<CartItem> cartItems, Context context, CartUpdateListener cartUpdateListener) {
        this.cartItems = cartItems;
        this.context = context;
        this.cartUpdateListener = cartUpdateListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.txtProduk.setText(Modul.upperCaseFirst(cartItem.getName()));
        holder.txtHarga.setText("Rp. " + Modul.numberFormat(String.valueOf(Integer.valueOf(cartItem.getPrice()))));
        holder.txtJumlah.setText(String.valueOf(cartItem.getQuantity()));

        Glide.with(context).load(API.ROOT_URL + cartItem.getImage()).into(holder.ivProduk);

        holder.checkbox.setChecked(cartItem.isChecked());

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cartItem.setChecked(isChecked);
            cartUpdateListener.onCartUpdated();
        });

        holder.ivHapus.setOnClickListener(view -> {
            removeFromCart(cartItem);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            cartUpdateListener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        ImageView ivProduk, ivHapus;
        TextView txtProduk, txtHarga, txtJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            ivProduk = itemView.findViewById(R.id.ivProduk);
            ivHapus = itemView.findViewById(R.id.ivHapus);
            txtProduk = itemView.findViewById(R.id.txtProduk);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            txtJumlah = itemView.findViewById(R.id.txtJumlah);
        }
    }

    private void removeFromCart(CartItem cartItem) {
        SPHelper spHelper = new SPHelper(context);
        String cartItemJson = new Gson().toJson(cartItem);
        spHelper.removeFromCart(cartItemJson);
        cartItems.remove(cartItem);
        cartUpdateListener.onCartUpdated(); // Update total harga dan checkout button
    }

    public interface CartUpdateListener {
        void onCartUpdated();
    }
}
