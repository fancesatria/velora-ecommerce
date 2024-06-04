package com.uas.nb_official.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uas.nb_official.Detail;
import com.uas.nb_official.Helper.API;
import com.uas.nb_official.Helper.Modul;
import com.uas.nb_official.Model.ProductModel;
import com.uas.nb_official.databinding.ItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    private List<ProductModel> data;

    public ProductAdapter(Context context, List<ProductModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding bind;
        bind = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductModel product = data.get(position);
        Glide.with(context).load(API.ROOT_URL+product.getGambar()).into(holder.bind.image);
        holder.bind.name.setText(Modul.upperCaseFirst(product.getNama()));
        holder.bind.price.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(product.getHarga()))));
        holder.bind.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Detail.class);
                i.putExtra("id_product", product.getId());
                i.putExtra("image", product.getGambar());
                i.putExtra("name", product.getNama());
                i.putExtra("price", product.getHarga());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        //if(data==null) return 0;
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding bind;
        public ViewHolder(ItemProductBinding itemView) {
            super(itemView.getRoot());

            bind = itemView;
        }
    }
}
