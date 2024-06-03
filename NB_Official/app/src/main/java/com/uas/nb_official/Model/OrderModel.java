package com.uas.nb_official.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class OrderModel {
    private int id;
    public String user_id, barang_id, harga, nama, jumlah, created_at, status;

    public OrderModel(int id, String user_id, String barang_id, String harga, String nama, String jumlah, String created_at, String status) {
        this.id = id;
        this.user_id = user_id;
        this.barang_id = barang_id;
        this.harga = harga;
        this.nama = nama;
        this.jumlah = jumlah;
        this.created_at = created_at;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBarang_id() {
        return barang_id;
    }

    public void setBarang_id(String barang_id) {
        this.barang_id = barang_id;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
