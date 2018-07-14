package com.carzis.repository.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;
    private static int timeoutSeconds = 90;

    public void NetworkClient(){

    }

    public static Retrofit getRetrofit(){

        if(retrofit==null){
            OkHttpClient.Builder builder =
                    new OkHttpClient.Builder()
                            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://carzis.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }

        return retrofit;
    }
}
