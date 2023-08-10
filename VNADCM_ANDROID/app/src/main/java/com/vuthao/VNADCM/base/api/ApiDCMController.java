package com.vuthao.VNADCM.base.api;

import android.content.Context;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class ApiDCMController extends ApiController {

    public ApiDCMController() {
    }

    public void getAllMasterData(String modified, ApiDCMListener callback) {
        Call<ApiObject<MasterData>> call = getApiRouteToken().getAllMasterData("DocumentAreaCategory,FavoriteFolder,DocumentType", modified);
        call.enqueue(new Callback<ApiObject<MasterData>>() {
            @Override
            public void onResponse(Call<ApiObject<MasterData>> call, Response<ApiObject<MasterData>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetAllDataSuccess(response.body().getData(), response.body().getDateNow());
                    } else {
                        callback.onGetAllDataError();
                    }
                } else {
                    callback.onGetAllDataError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<MasterData>> call, Throwable t) {
                callback.onGetAllDataError();
            }
        });
    }

    public void updateAllMasterData(Context context, ApiDCMListener callback) {
        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(context);
        String dateNow = sharedPreferencesController.getModified();

        Call<ApiObject<MasterData>> call = getApiRouteToken().getAllMasterData("DocumentAreaCategory,FavoriteFolder,DocumentType,CurrentUser", dateNow);
        call.enqueue(new Callback<ApiObject<MasterData>>() {
            @Override
            public void onResponse(Call<ApiObject<MasterData>> call, Response<ApiObject<MasterData>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        MasterData masterData = response.body().getData();
                        new RealmCategoryController().addItems(masterData.getDocumentAreaCategory());
                        new RealmFavoriteController().addItems(masterData.getFavoriteFolder());

                        masterData.getCurrentUser().get(0).setDefaultLanguageid(Integer.parseInt(sharedPreferencesController.getLocaleId()));
                        CurrentUser.getInstance().setUser(masterData.getCurrentUser().get(0));
                        sharedPreferencesController.saveModified(dateNow);
                        sharedPreferencesController.saveUser(new Gson().toJson(CurrentUser.getInstance().getUser()));

                        callback.onGetAllDataSuccess(response.body().getData(), response.body().getDateNow());
                    } else {
                        callback.onGetAllDataError();
                    }
                } else {
                    callback.onGetAllDataError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<MasterData>> call, Throwable t) {
                callback.onGetAllDataError();
            }
        });
    }

    public void getLogContext() {
        Call<Status> call = getApiRouteToken().getLogContext();
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    public interface ApiDCMListener {
        void onGetAllDataSuccess(MasterData masterData, String dateNow);

        void onGetAllDataError();
    }
}
