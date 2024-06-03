package com.uas.nb_official.Helper;

import com.uas.nb_official.Model.LoginModel;
import com.uas.nb_official.Model.OrderModel;
import com.uas.nb_official.Model.ProductModel;
import com.uas.nb_official.Model.RegisterModel;
import com.uas.nb_official.Response.LoginResponse;
import com.uas.nb_official.Response.RegisterResponse;
import com.uas.nb_official.Response.PaymentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
    //AUTH
    @POST("custom-login_api")
    Call<LoginResponse> login(@Body LoginModel loginModel);
    @POST("custom-registration_api")
    Call<RegisterResponse> register(@Body RegisterModel registerModel);

    //Product
    @GET("barangs_api")//get
    Call<List<ProductModel>> getDataProduct();
    @GET("barangs_api/{id}")
    Call<List<ProductModel>> getDataProduct(@Path("id") int id);

    //Order
    @GET("orders_api/user/{id}")
    Call<List<OrderModel>> getDataOrder(@Path("id") int id);

    @POST("orders/update_status/{id}")
    Call<Void> updateStatus(@Path("id") int id);
    @POST("orders/{user_id}/{barang_id}/{jumlah}")
    Call<PaymentResponse> createPayment(
            @Path("user_id") String user_id,
            @Path("barang_id") String barang_id,
            @Path("jumlah") String jumlah
    );
}
