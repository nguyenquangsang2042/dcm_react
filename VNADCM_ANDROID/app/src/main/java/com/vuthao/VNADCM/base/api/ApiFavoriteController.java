package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class ApiFavoriteController extends ApiController {

    public ApiFavoriteController() {
    }

    public void getFavorites(ApiFavoriteListener callback) {
        Call<ApiList<FavoriteFolder>> call = getApiRoute().getFavorites(CurrentUser.getInstance().getUser().getDefaultLanguageid() + "");
        call.enqueue(new Callback<ApiList<FavoriteFolder>>() {
            @Override
            public void onResponse(Call<ApiList<FavoriteFolder>> call, Response<ApiList<FavoriteFolder>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetFavoriteSuccess(response.body().getData());
                    } else {
                        callback.onGetFavoriteError();
                    }
                } else {
                    callback.onGetFavoriteError();
                }
            }

            @Override
            public void onFailure(Call<ApiList<FavoriteFolder>> call, Throwable t) {
                callback.onGetFavoriteError();
            }
        });
    }

    public void getDocumentFavoriteById(String id, int limit, int offset, ApiDocumentFavoriteListener callback) {
        Call<ApiObject<DocumentFavorite>> call = getApiRoute().getDocumentFavoriteById(id, limit + "", offset + "");
        call.enqueue(new Callback<ApiObject<DocumentFavorite>>() {
            @Override
            public void onResponse(Call<ApiObject<DocumentFavorite>> call, Response<ApiObject<DocumentFavorite>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetDocumentFavoriteSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetDocumentFavoriteError();
                    }
                } else {
                    callback.onGetDocumentFavoriteError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<DocumentFavorite>> call, Throwable t) {
                callback.onGetDocumentFavoriteError();
            }
        });
    }

    public interface ApiDocumentFavoriteListener {
        void onGetDocumentFavoriteSuccess(ArrayList<DocumentFavorite> documentFavorites, int totalRecord);
        void onGetDocumentFavoriteError();
    }

    public interface ApiFavoriteListener {
        void onGetFavoriteSuccess(ArrayList<FavoriteFolder> favorites);
        void onGetFavoriteError();
    }
}
