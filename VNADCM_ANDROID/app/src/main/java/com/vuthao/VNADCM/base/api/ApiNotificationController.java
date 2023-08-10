package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiData;
import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Notify;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class ApiNotificationController extends ApiController {

    public void getNotifies(int index, int limit, ApiNotificationListener callback) {
        Call<ApiObject<Notify>> call = getApiRoute().getNotifies(index + "", limit + "");
        call.enqueue(new Callback<ApiObject<Notify>>() {
            @Override
            public void onResponse(Call<ApiObject<Notify>> call, Response<ApiObject<Notify>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetNotificationSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetNotificationError();
                    }
                } else {
                    callback.onGetNotificationError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<Notify>> call, Throwable t) {
                callback.onGetNotificationError();
            }
        });
    }

    public void getUnReadNotifies(int index, int limit, ApiNotificationListener callback) {
        Call<ApiObject<Notify>> call = getApiRoute().getUnReadNotifies(index + "", limit + "");
        call.enqueue(new Callback<ApiObject<Notify>>() {
            @Override
            public void onResponse(Call<ApiObject<Notify>> call, Response<ApiObject<Notify>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetNotificationSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetNotificationError();
                    }
                } else {
                    callback.onGetNotificationError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<Notify>> call, Throwable t) {
                callback.onGetNotificationError();
            }
        });
    }

    public void getCountNotify(ApiNotifyCountListener callback) {
        Call<ApiData> call = getApiRouteToken().getCountNotify();
        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetCountNotifySuccess(Integer.parseInt(response.body().getData()));
                    } else {
                        callback.onGetCountNotifySuccess(0);
                    }
                } else {
                    callback.onGetCountNotifySuccess(0);
                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                callback.onGetCountNotifySuccess(0);
            }
        });
    }

    public void markAsRead(String rid) {
        Call<Status> call = getApiRouteToken().markAsRead(rid);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    public interface ApiNotificationListener {
        void onGetNotificationSuccess(ArrayList<Notify> notifies, int totalRecord);
        void onGetNotificationError();
    }

    public interface ApiNotifyCountListener {
        void onGetCountNotifySuccess(int count);
    }
}
