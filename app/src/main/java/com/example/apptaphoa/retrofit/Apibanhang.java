package com.example.apptaphoa.retrofit;

import com.example.apptaphoa.model.Loaispmodel;
import com.example.apptaphoa.model.Sanphamnewmodel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apibanhang {
    @GET("getloaisp.php")
    Observable<Loaispmodel> getloaisp();

    @GET("getspmoi.php")
    Observable<Sanphamnewmodel> getsanphamnewmodel();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<Sanphamnewmodel> getsanpham(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
