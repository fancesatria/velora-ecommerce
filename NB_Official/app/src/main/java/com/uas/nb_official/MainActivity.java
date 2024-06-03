package com.uas.nb_official;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.uas.nb_official.Cart.CartFragment;
import com.uas.nb_official.Home.HomeFragment;
import com.uas.nb_official.Transaction.OrderFragment;
import com.uas.nb_official.databinding.ActivityMainBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bind;
    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
//        getSupportActionBar().hide();
        meow();
    }

    public void meow(){
        meowBottomNavigation = bind.meownav;

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_cart));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_order));

        meowBottomNavigation.show(1, true);
        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1 :
                        replace(new HomeFragment());
                        break;
                    case 2:
                        replace(new CartFragment());
                        break;
                    case 3 :
                        replace(new OrderFragment());
                        break;
                }
                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(Integer.valueOf(R.id.framelayout), fragment).commit();
    }
}