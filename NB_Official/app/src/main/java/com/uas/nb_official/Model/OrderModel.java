package com.uas.nb_official.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class OrderModel {
    public String snap_token, created_at, status_order, total_harga;

    public OrderModel(String snap_token, String created_at, String status, String total_harga) {
        this.snap_token = snap_token;
        this.created_at = created_at;
        this.status_order = status;
        this.total_harga = total_harga;
    }

    public String getSnap_token() {
        return snap_token;
    }

    public void setSnap_token(String snap_token) {
        this.snap_token = snap_token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus_order() {
        return status_order;
    }

    public void setStatus_order(String status_order) {
        this.status_order = status_order;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String newDate() throws ParseException {
        // Parsing tanggal input
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date tanggal = inputFormat.parse(getCreated_at());

        // Memformat ulang tanggal
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.US);
        return outputFormat.format(tanggal);
    }
}
