package com.uas.nb_official.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.uas.nb_official.Adapter.ProductAdapter;
import com.uas.nb_official.Adapter.SliderAdapter;
import com.uas.nb_official.Component.ErrorDialog;
import com.uas.nb_official.Component.LoadingDialog;
import com.uas.nb_official.Helper.API;
import com.uas.nb_official.Helper.SPHelper;
import com.uas.nb_official.Auth.Login;
import com.uas.nb_official.Model.ProductModel;
import com.uas.nb_official.Model.SliderItem;
import com.uas.nb_official.R;
import com.uas.nb_official.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding bind;
    SliderView sliderView;
    private SliderAdapter adapter;
    List<ProductModel> data = new ArrayList<>();
    ProductAdapter productAdapter;
    private SPHelper spHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SPHelper sp;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentHomeBinding.inflate(inflater, container, false);

        load();
        bind.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return bind.getRoot();
    }

    public void load(){
        sp = new SPHelper(getContext());
        slider();
        fetchData();
        bind.greetings.setText("Hello, "+sp.getUsername());
    }


    public void slider(){
        sliderView = bind.imageSlider;
        adapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        SliderItem sliderItem = new SliderItem();
        sliderItem.setImageUrl("https://marketplace.canva.com/EAFKwirl3N8/1/0/1600w/canva-brown-minimalist-fashion-product-banner-iRHpbHTqh-A.jpg");


//        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
//            @Override
//            public void onIndicatorClicked(int position) {
//                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
//            }
//        });
        renewItems();
        removeLastItem();
    }

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("");
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://img.freepik.com/free-vector/flat-design-minimal-boutique-sale-background_23-2149337460.jpg");

            } else {
                sliderItem.setImageUrl("https://img.freepik.com/free-vector/horizontal-sale-banner-template_23-2148897328.jpg");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem() {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void fetchData(){
        LoadingDialog.load(getContext());
        Call<List<ProductModel>> karyaGetRespCall = API.getRetrofit(getContext()).getDataProduct();
        karyaGetRespCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                LoadingDialog.close();
                if(response.isSuccessful()){

                    if (response.body().size() == 0 || response.body()==null){
                        Toast.makeText(getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        data = response.body();
                        bind.item.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        productAdapter = new ProductAdapter(getContext(), data);
                        bind.item.setAdapter(productAdapter);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    ErrorDialog.message(getContext(), getString(R.string.cant_access), bind.getRoot());
                    //Toast.makeText(getContext(), response.body().getJudul(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(getContext(), getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(){
        spHelper = new SPHelper(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle("Hapus Item")
                .setMessage("Ingin keluar?")
                .setPositiveButton("Iya", (dialog, which) -> {
                    spHelper.clearData();

                    startActivity(new Intent(getContext(), Login.class));
                    getActivity().finish();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}