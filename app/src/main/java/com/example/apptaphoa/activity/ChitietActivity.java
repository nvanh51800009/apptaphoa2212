package com.example.apptaphoa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apptaphoa.R;
import com.example.apptaphoa.model.Sanphamnew;

import java.text.DecimalFormat;

public class ChitietActivity extends AppCompatActivity {
    TextView tensp, giap, mota;
    Button btnthem;
    ImageView imganh;
    Spinner spinner;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        initView();
        Actiontoolbar();
        initData();
    }

    private void initData() {
        Sanphamnew sanphamnew = (Sanphamnew) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanphamnew.getTensp());
        mota.setText(sanphamnew.getMota());
        Glide.with(getApplicationContext()).load(sanphamnew.getHinhanh()).into(imganh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        giap.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanphamnew.getGiasp())) +" VNĐ");


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

    private void initView() {
        tensp = findViewById(R.id.txttenspct);
        giap = findViewById(R.id.txtguachitiet);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btbthemgiohang);
        spinner = findViewById(R.id.spinnerr);
        imganh =findViewById(R.id.imgchitiet);
        toolbar =findViewById(R.id.toolbar);
    }



}