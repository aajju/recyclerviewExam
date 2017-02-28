package com.aajju.recyclerviewexam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by aajju on 2017-02-24.
 */

public interface Api {

    @GET("memo/")
    Call<List<Memo>> getMemoList();

    @PUT("memo/")
    @FormUrlEncoded
    Call<Void> updateMemo(@Field("memo") String memo);

    @POST("memo/")
    @FormUrlEncoded
    Call<Void> addMemo(@Field("memo") String memo);

    @DELETE("memo/{id}")
//    @FormUrlEncoded
    Call<Void> deleteMemo(@Path("id") int id);
}
