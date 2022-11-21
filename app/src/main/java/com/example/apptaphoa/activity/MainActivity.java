package com.example.apptaphoa.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptaphoa.R;
import com.example.apptaphoa.adapter.Loaispadapter;
import com.example.apptaphoa.adapter.Sanphamnewadapter;
import com.example.apptaphoa.model.Loaisp;
import com.example.apptaphoa.model.Sanphamnew;
import com.example.apptaphoa.model.Sanphamnewmodel;
import com.example.apptaphoa.retrofit.Apibanhang;
import com.example.apptaphoa.retrofit.Retrofitclient;
import com.example.apptaphoa.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    Loaispadapter loaispadapter;
    List<Loaisp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Apibanhang apibanhang;
    List<Sanphamnew> mangspnew;
    Sanphamnewadapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apibanhang = Retrofitclient.getInstance(Utils.BASE_URL).create(Apibanhang.class);
        Anhxa();
        ActionBar();
        //ActionViewFlipper();
        if(isConnected(this)){
            //Toast.makeText(getApplicationContext(),"ok", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getLoaisanpham();
            getspnew();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(),"Không có internet! Vui lòng kết nối!", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent banh = new Intent(getApplicationContext(), BanhActivity.class);
                        banh.putExtra("loai", 1);
                        startActivity(banh);
                        break;
                    case 2:
                        Intent nuoc = new Intent(getApplicationContext(), BanhActivity.class);
                        nuoc.putExtra("loai", 2);
                        startActivity(nuoc);
                        break;
                }
            }
        });
    }

    private void getspnew() {
        compositeDisposable.add(apibanhang.getsanphamnewmodel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamnewmodel -> {
                            if (sanphamnewmodel.isSuccess()){
                                mangspnew = sanphamnewmodel.getResult();
                                spAdapter = new Sanphamnewadapter(getApplicationContext(),mangspnew);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối bới server" +  throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaisanpham() {
        compositeDisposable.add(apibanhang.getloaisp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaispmodel -> {
                            if(loaispmodel.isSuccess()){
                                //Toast.makeText(getApplicationContext(), loaispmodel.getResult().get(0).getTensp(), Toast.LENGTH_LONG).show();
                                mangloaisp = loaispmodel.getResult();
                                loaispadapter = new Loaispadapter(getApplicationContext(),mangloaisp);
                                listViewManHinhChinh.setAdapter(loaispadapter);
                            }
                        }
                ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/bachhoaxanh/banners/6010/banner-landingpage-6010-02112022131854.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/bachhoaxanh/banners/5562/mi-gia-soc-09102022185049.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/bachhoaxanh/banners/5562/nuoc-khuyen-mai-17102022113517.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/bachhoaxanh/banners/5562/giay-ve-sinh-khan-uot-giam-den-49-khi-mua-san-pham-thu-2-01112022212612.jpg");
        for(int i = 0; i<mangquangcao.size(); i++  ){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigatioview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        //khoitao
        mangloaisp = new ArrayList<>();
        mangspnew = new ArrayList<>();
        //khoitaoadpater
        //loaispadapter = new Loaispadapter(getApplicationContext(),mangloaisp);
        //listViewManHinhChinh.setAdapter(loaispadapter);
    }
    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if( (wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}