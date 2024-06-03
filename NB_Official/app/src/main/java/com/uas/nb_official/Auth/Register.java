package com.uas.nb_official.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uas.nb_official.Component.ErrorDialog;
import com.uas.nb_official.Component.LoadingDialog;
import com.uas.nb_official.Component.SuccessDialog;
import com.uas.nb_official.Helper.API;
import com.uas.nb_official.Helper.SPHelper;
import com.uas.nb_official.MainActivity;
import com.uas.nb_official.Model.RegisterModel;
import com.uas.nb_official.R;
import com.uas.nb_official.Response.RegisterResponse;
import com.uas.nb_official.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding bind;
    EditText etEmail, etPassword, etConfirm, etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }

    public void buatAkun(View view) {
        SPHelper sp = new SPHelper(Register.this);
        if (validasi()){

        } else {
            RegisterModel rg = new RegisterModel();
            rg.setEmail(bind.email.getText().toString());
            rg.setPassword(bind.password.getText().toString());
            rg.setName(bind.username.getText().toString());
            prosesBuatAkun(rg);

        }
    }

    public void prosesBuatAkun(RegisterModel registerModel){
        LoadingDialog.load(Register.this);
        SPHelper sp = new SPHelper(Register.this);
        Call<RegisterResponse> registerResponseCall = API.getRetrofit().register(registerModel);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful()){
                    //simpan token dan username
                    sp.setToken(response.body().getToken());
                    sp.setEmail(response.body().getData().getEmail());
                    sp.setUsername(response.body().getData().getName());
                    sp.setIdPengguna(response.body().getData().getId());

                    SuccessDialog.message(Register.this, "Akun berhasil dibuat", bind.getRoot());

                    startActivity(new Intent(Register.this, MainActivity.class));
                    finish();

                } else {
                    ErrorDialog.message(Register.this, "Tidak dapat membuat akun", bind.getRoot());
                    Toast.makeText(Register.this, String.valueOf(response), Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(Register.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                LoadingDialog.close();
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validasi() {
        etEmail = bind.email;
        etPassword = bind.password;
        etConfirm = bind.confirmpassword;
        etUsername = bind.username;
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        if(etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etConfirm.getText().toString().isEmpty() || etUsername.getText().toString().isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Harap diisi");

            etUsername.requestFocus();
            etUsername.setError("Harap diisi");

            etConfirm.requestFocus();
            etConfirm.setError("Harap diisi");
            return true;

        } else if (!etPassword.getText().toString().matches(etConfirm.getText().toString())){
            ErrorDialog.message(Register.this, getString(R.string.unmatch), bind.getRoot());
            return true;
        } else if (!etEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            ErrorDialog.message(Register.this, getString(R.string.is_email), bind.getRoot());
            return true;
        } else if(etPassword.getText().toString().length() < 8 || etConfirm.getText().toString().length() < 8){
            ErrorDialog.message(Register.this, "Password harus terdisi dari minimal 8 digit, angka, dan karakter", bind.getRoot());
            return true;
        } else {
            Toast.makeText(this, "Harap tunggu...", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}