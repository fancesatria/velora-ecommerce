package com.uas.nb_official.Component;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.uas.nb_official.R;

public class SuccessDialog {

    static Snackbar snackbar;

    public static void message(Context context,String message, View view){
        snackbar = Snackbar.make(view,message, BaseTransientBottomBar.LENGTH_SHORT);

        snackbar.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.teal));

        snackbar.show();
    }
}
