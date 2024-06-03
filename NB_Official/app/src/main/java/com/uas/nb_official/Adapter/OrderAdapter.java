package com.uas.nb_official.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uas.nb_official.Helper.Modul;
import com.uas.nb_official.Model.OrderModel;
import com.uas.nb_official.R;
import com.uas.nb_official.Transaction.OrderFragment;

import java.text.ParseException;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private OrderFragment context;
    private List<OrderModel> data;

    public OrderAdapter(OrderFragment context, List<OrderModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderModel orderModel = data.get(position);
        try {
            holder.tanggal.setText(String.valueOf(orderModel.newDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.nama.setText(orderModel.getNama());
        holder.status.setText(orderModel.getStatus());
        holder.jumlah.setText(orderModel.getJumlah());
        holder.harga.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(orderModel.getHarga()) * Integer.valueOf(orderModel.getJumlah()))));

        if (orderModel.getStatus().equalsIgnoreCase("success")) {
            holder.viewColor.setBackgroundColor(ContextCompat.getColor(context.getContext(), R.color.teal));
            holder.btnBayar.setVisibility(View.GONE);
        } else {
            holder.viewColor.setBackgroundColor(ContextCompat.getColor(context.getContext(), R.color.maroon));
            holder.btnBayar.setVisibility(View.VISIBLE);
        }

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.updateStatus(orderModel.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, jumlah, harga, tanggal, status;
        LinearLayout viewColor;
        Button btnBayar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.txtBarang);
            jumlah = itemView.findViewById(R.id.txtJumlah);
            harga = itemView.findViewById(R.id.txtHarga);
            tanggal = itemView.findViewById(R.id.txtDate);
            status = itemView.findViewById(R.id.txtStatus);
            viewColor = itemView.findViewById(R.id.viewColor);
            btnBayar = itemView.findViewById(R.id.btnBayar);
        }
    }
}
