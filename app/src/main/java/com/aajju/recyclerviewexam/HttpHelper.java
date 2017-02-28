package com.aajju.recyclerviewexam;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aajju on 2017-02-24.
 */

public class HttpHelper {

    private static final String RELEASE_BASE_URL = "http://wcwsbs.iptime.org:9999/";

    public static Api getApi(){
        Retrofit retrofit = createRetrofit();
        return  retrofit.create(Api.class);
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RELEASE_BASE_URL)
                .client(getClient())
                .build();
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .build();
    }


}
