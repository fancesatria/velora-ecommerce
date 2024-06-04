package com.uas.nb_official.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    @SerializedName("user_id")
    private int user_id;

    @SerializedName("items")
    private List<Item> items;

    public PaymentModel(int userId, List<Item> items) {
        this.user_id = userId;
        this.items = items;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        @SerializedName("id")
        private String id; // Changed from int to String

        @SerializedName("jumlah")
        private String jumlah; // Changed from int to String

        public Item(String id, String jumlah) {
            this.id = id;
            this.jumlah = jumlah;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }
    }
}
