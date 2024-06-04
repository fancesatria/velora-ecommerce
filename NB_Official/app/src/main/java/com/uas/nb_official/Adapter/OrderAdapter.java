package com.uas.nb_official.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.uas.nb_official.Transaction.DetailOrder;
import com.uas.nb_official.Transaction.OrderFragment;

import java.text.ParseException;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private OrderFragment fragment;
    private List<OrderModel> data;

    public OrderAdapter(OrderFragment context, List<OrderModel> data) {
        this.fragment = context;
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

        holder.status.setText(orderModel.getStatus_order());
        holder.total.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(orderModel.getTotal_harga()))));

        if (orderModel.getStatus_order().equalsIgnoreCase("success")) {
            holder.status.setBackgroundResource(R.drawable.background_status_teal);
            holder.btnBayar.setVisibility(View.GONE);
        } else {
            holder.status.setBackgroundResource(R.drawable.backgound_status_maroon);
            holder.btnBayar.setVisibility(View.GONE);
        }

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.updateStatus(orderModel.getSnap_token());
            }
        });

        holder.linearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(fragment.getContext(), DetailOrder.class);
                i.putExtra("snap_token", orderModel.getSnap_token());
                i.putExtra("total_harga", orderModel.getTotal_harga());
                i.putExtra("status_order", orderModel.getStatus_order());
                try {
                    i.putExtra("tanggal_transaksi", orderModel.newDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                fragment.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total, tanggal, status;
        Button btnBayar;
        LinearLayout linearOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            total = itemView.findViewById(R.id.txtTotal);
            tanggal = itemView.findViewById(R.id.txtDate);
            status = itemView.findViewById(R.id.txtStatus);
            btnBayar = itemView.findViewById(R.id.btnBayar);
            linearOrder = itemView.findViewById(R.id.linearOrder);
        }
    }
}
