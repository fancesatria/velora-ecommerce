package com.uas.nb_official.Response;

import com.uas.nb_official.Model.OrderDetailModel;
import java.util.List;

public class OrderDetailResponse {
    private boolean success;
    private List<OrderDetailModel> orders;
    private int total_harga;
    private String status;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrderDetailModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetailModel> orders) {
        this.orders = orders;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}