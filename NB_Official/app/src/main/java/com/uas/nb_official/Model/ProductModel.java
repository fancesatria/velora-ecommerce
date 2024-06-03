package com.uas.nb_official.Model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    private String nama, harga, gambar;
    private int id;

    public ProductModel(String nama, String harga, String gambar, int id) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
