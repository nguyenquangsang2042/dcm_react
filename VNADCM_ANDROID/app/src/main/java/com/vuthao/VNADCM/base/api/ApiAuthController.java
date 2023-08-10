package com.vuthao.VNADCM.base.api;

import com.google.gson.JsonObject;
import com.vuthao.VNADCM.base.model.ApiData;
import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.Settings;
import com.vuthao.VNADCM.base.model.app.User;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 09/02/2023.
 */
public class ApiAuthController extends ApiController {

    public void auth(String data, ApiAuthListener callback) {
        Call<ApiData> call = getApiRouteToken().auth(data);
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onAuthSuccess();
                    } else {
                        if (response.body().getMess().getKey().equals("199")) {
                            auth(data, callback);
                        } else {
                            callback.onAuthError("Thông tin đăng nhập không chính xác");
                        }
                    }
                } else {
                    callback.onAuthError("Thông tin đăng nhập không chính xác");
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    callback.onAuthError("Không có kết nối mạng");
                } else {
                    callback.onAuthError("Thông tin đăng nhập không chính xác");
                }
            }
        });
    }

    public void authCMS(Callback<Status> callback) {
        Call<Status> call = getApiRouteToken().authCMS();
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getUserInfo(String data, ApiAuthUserListener callback) {
        Call<ApiList<User>> call = getApiRouteToken().getCurrentUserInfo(data);
        call.enqueue(new Callback<ApiList<User>>() {
            @Override
            public void onResponse(Call<ApiList<User>> call, Response<ApiList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onAuthUserSuccess(response.body().getData().get(0));
                    } else {
                        callback.onAuthUserError("Thông tin đăng nhập không chính xác");
                    }
                } else {
                    callback.onAuthUserError("Thông tin đăng nhập không chính xác");
                }
            }

            @Override
            public void onFailure(Call<ApiList<User>> call, Throwable t) {
                if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
                    callback.onAuthUserError("Không có kết nối mạng");
                } else {
                    callback.onAuthUserError("Thông tin đăng nhập không chính xác");
                }
            }
        });
    }

    public void signOut(String deviceId) {
        Call<Status> call = getApiRouteToken().signOut(deviceId);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    public void checkCurrentVersion(ApiVersionListener callback) {
        Call<ApiList<Settings>> call = getApiRouteToken().getCurrentVersion("Android_AppVer");
        call.enqueue(new Callback<ApiList<Settings>>() {
            @Override
            public void onResponse(Call<ApiList<Settings>> call, Response<ApiList<Settings>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetCurrentVersionSuccess(response.body().getData().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiList<Settings>> call, Throwable t) {
            }
        });
    }

    public void getTokenWebView(ApiTokenWebViewListener callback) {
        Call<JsonObject> call = getApiRouteToken().getTokenMobileWebView();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject h = response.body();
                    if (h.has("status") && h.get("status").getAsString().equals("SUCCESS")) {
                        callback.onGetTokenSuccess(h.get("data").getAsString());
                    } else {
                        callback.onGetTokenError();
                    }
                } else {
                    callback.onGetTokenError();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onGetTokenError();
            }
        });
    }

    public interface ApiTokenWebViewListener {
        void onGetTokenSuccess(String token);

        void onGetTokenError();
    }

    public interface ApiAuthUserListener {
        void onAuthUserSuccess(User user);

        void onAuthUserError(String error);
    }

    public interface ApiAuthListener {
        void onAuthSuccess();

        void onAuthError(String error);
    }

    public interface ApiVersionListener {
        void onGetCurrentVersionSuccess(Settings settings);
    }
}
