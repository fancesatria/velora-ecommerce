package com.uas.nb_official.Helper;

import com.uas.nb_official.Model.LoginModel;
import com.uas.nb_official.Model.OrderModel;
import com.uas.nb_official.Model.PaymentModel;
import com.uas.nb_official.Model.ProductModel;
import com.uas.nb_official.Model.RegisterModel;
import com.uas.nb_official.Response.LoginResponse;
import com.uas.nb_official.Response.OrderDetailResponse;
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
    @GET("orders_api/user/{id}") // Index order
    Call<List<OrderModel>> getDataOrder(@Path("id") int id);

    @GET("orders_api/detail/{snap_token}") // Order detail
    Call<OrderDetailResponse> getOrderDetails(@Path("snap_token") String snapToken);

    @POST("orders/update_status/{snap_token}") // Update status
    Call<Void> updateStatus(@Path("snap_token") String snap_token);

    @POST("orders") // Create order
    Call<PaymentResponse> createPayment(@Body PaymentModel paymentModel);
}
