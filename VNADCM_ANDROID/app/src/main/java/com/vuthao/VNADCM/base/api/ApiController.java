package com.vuthao.VNADCM.base.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nhum Lê Sơn Thạch on 09/02/2023.
 */
public class ApiController {
    Route getApiRoute() {
        return getApiRouteToken();
    }

    public Route getApiRouteToken() {
        return getApiRouteToken("");
    }

    Route getApiRouteToken(String url) {
        if (url.contains(Constants.BASE_URL)) {
            url = url.replaceAll(Constants.BASE_URL, "");
        }
        url = Constants.BASE_URL + url;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        PersistentCookieStore cookieStore = new PersistentCookieStore(DCMApplication.context);
        CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

        httpClient
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept", "application/x-www-form-urlencoded")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build());

        Retrofit retrofit = builder.build();
        return retrofit.create(Route.class);
    }
}
