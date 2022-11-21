package com.example.apptaphoa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.apptaphoa.R;
import com.example.apptaphoa.adapter.BanhAdapter;
import com.example.apptaphoa.model.Sanphamnew;
import com.example.apptaphoa.retrofit.Apibanhang;
import com.example.apptaphoa.retrofit.Retrofitclient;
import com.example.apptaphoa.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BanhActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Apibanhang apibanhang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    BanhAdapter adapterbanh;
    List<Sanphamnew> sanphamnewList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banh);
        apibanhang = Retrofitclient.getInstance(Utils.BASE_URL).create(Apibanhang.class);
        loai = getIntent().getIntExtra("loai", 1);
        Anhxa();
        Actiontoolbar();
        getData(page);
        addEventLoad();

    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if(linearLayoutManager.findFirstCompletelyVisibleItemPosition()==sanphamnewList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sanphamnewList.add(null);
                adapterbanh.notifyItemInserted(sanphamnewList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanphamnewList.remove(sanphamnewList.size()-1);
                adapterbanh.notifyItemRemoved(sanphamnewList.size());
                page = page + 1;
                getData(page);;
                adapterbanh.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apibanhang.getsanpham(page,loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanphamnewmodel -> {
                            if(sanphamnewmodel.isSuccess()){
                                if(adapterbanh==null){
                                    sanphamnewList = sanphamnewmodel.getResult();
                                    adapterbanh = new BanhAdapter(getApplicationContext(), sanphamnewList);
                                    recyclerView.setAdapter(adapterbanh);
                                }else {
                                    int vitri = sanphamnewList.size()-1;
                                    int sladd = sanphamnewmodel.getResult().size();
                                    for(int i = 0; i<sladd; i++){
                                        sanphamnewList.add(sanphamnewmodel.getResult().get(i));
                                    }
                                    adapterbanh.notifyItemRangeInserted(vitri,sladd);
                                }

                            }else {
                                Toast.makeText(getApplicationContext(), "het du lieu roi", Toast.LENGTH_LONG).show();;
                                isLoading = true;
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarbanh);
        recyclerView = findViewById(R.id.rcylbanh);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanphamnewList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}