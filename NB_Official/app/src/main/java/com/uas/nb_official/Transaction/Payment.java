package com.uas.nb_official.Transaction;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.uas.nb_official.Component.ErrorDialog;
import com.uas.nb_official.Component.LoadingDialog;
import com.uas.nb_official.Component.SuccessDialog;
import com.uas.nb_official.Helper.API;
import com.uas.nb_official.MainActivity;
import com.uas.nb_official.R;
import com.uas.nb_official.databinding.ActivityPaymentBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity {
    ActivityPaymentBinding bind;
    String snap_token, snap_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        load();
    }

    public void load() {
        LoadingDialog.load(Payment.this);

        WebSettings webSettings = bind.webviewPayment.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        bind.webviewPayment.setWebChromeClient(new WebChromeClient());
        WebView.setWebContentsDebuggingEnabled(true);

        snap_token = getIntent().getStringExtra("snap_token");
        snap_url = "https://app.sandbox.midtrans.com/snap/v2/vtweb/" + snap_token;

        bind.webviewPayment.loadUrl(snap_url);
        bind.webviewPayment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LoadingDialog.load(Payment.this);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadingDialog.close();
                bind.btnHome.setVisibility(View.VISIBLE);
                bind.btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateStatus(getIntent().getIntExtra("id_barang",0));
                    }
                });
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                LoadingDialog.close();
                Toast.makeText(Payment.this, "Failed to load URL", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void navigateToNextFragment() {
        startActivity(new Intent(Payment.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (bind.webviewPayment.canGoBack()) {
            bind.webviewPayment.goBack();
        } else {
            super.onBackPressed();
            navigateToNextFragment();
        }
    }

    public void updateStatus(int id){
        new AlertDialog.Builder(Payment.this)
                .setTitle("Konfirmasi")
                .setMessage("Update status pembayaran?")
                .setPositiveButton("Iya", (dialog, which) -> {
                    LoadingDialog.load(Payment.this);
                    Call<Void> call = API.getRetrofit(Payment.this).updateStatus(id);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            LoadingDialog.close();
                            if (response.isSuccessful()){
                                SuccessDialog.message(Payment.this, getString(R.string.saved), bind.getRoot());
                                navigateToNextFragment();
                            } else {
                                ErrorDialog.message(Payment.this, getString(R.string.unsaved), bind.getRoot());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            LoadingDialog.close();
                            Toast.makeText(Payment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
