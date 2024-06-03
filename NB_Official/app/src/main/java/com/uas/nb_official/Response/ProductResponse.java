package com.uas.nb_official.Response;

import com.uas.nb_official.Model.ProductModel;

import java.util.List;

public class ProductResponse {
    private String pesan;
    private int status;
    private List<ProductModel> data;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }
}
