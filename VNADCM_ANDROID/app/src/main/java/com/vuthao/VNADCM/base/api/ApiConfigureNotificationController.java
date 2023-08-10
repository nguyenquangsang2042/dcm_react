package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.app.CurrentUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class ApiConfigureNotificationController extends ApiController {
    public ApiConfigureNotificationController() {
    }

    public void getConfigureNotification(ApiConfigureNotificationListener callback) {
        Call<ApiObject<ConfigureNotification>> call = getApiRoute().getConfigNotification();
        call.enqueue(new Callback<ApiObject<ConfigureNotification>>() {
            @Override
            public void onResponse(Call<ApiObject<ConfigureNotification>> call, Response<ApiObject<ConfigureNotification>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetConfigureSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0));
                    } else {
                        callback.onGetConfigureError();
                    }
                } else {
                    callback.onGetConfigureError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<ConfigureNotification>> call, Throwable t) {
                callback.onGetConfigureError();
            }
        });
    }

    public void updateConfigureNotification(String data, ApiUpdateConfigureNotificationListener callback) {
        Call<Status> call = getApiRoute().updateConfigureNotification(data);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onUpdateConfigureNotificationSuccess();
                    } else {
                        callback.onUpdateConfigureNotificationError();
                    }
                } else {
                    callback.onUpdateConfigureNotificationError();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                callback.onUpdateConfigureNotificationError();
            }
        });
    }

    public interface ApiConfigureNotificationListener {
        void onGetConfigureSuccess(ArrayList<ConfigureNotification> configureNotifications, MoreInfo moreInfo);

        void onGetConfigureError();
    }

    public interface ApiUpdateConfigureNotificationListener {
        void onUpdateConfigureNotificationSuccess();

        void onUpdateConfigureNotificationError();
    }
}
