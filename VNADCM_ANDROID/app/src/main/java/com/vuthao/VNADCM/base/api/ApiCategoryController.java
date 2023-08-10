package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class ApiCategoryController extends ApiController {

    public ApiCategoryController() {
    }

    public void getCategories(ApiCategoryListener callback) {
        Call<ApiList<DocumentAreaCategory>> call = getApiRoute().getCategories(CurrentUser.getInstance().getUser().getDefaultLanguageid() + "");
        call.enqueue(new Callback<ApiList<DocumentAreaCategory>>() {
            @Override
            public void onResponse(Call<ApiList<DocumentAreaCategory>> call, Response<ApiList<DocumentAreaCategory>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetCategorySuccess(response.body().getData());
                    } else {
                        callback.onGetCategorySuccess(new ArrayList<>());
                    }
                } else {
                    callback.onGetCategorySuccess(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiList<DocumentAreaCategory>> call, Throwable t) {
                callback.onGetCategorySuccess(new ArrayList<>());
            }
        });
    }

    public void getListDocumentCategory(int limit, int offset, String data, ApiListDocumentCategoryListener callback) {
        Call<ApiObject<DocumentCategory>> call = getApiRoute().getListDocumentCategory(CurrentUser.getInstance().getUser().getDefaultLanguageid() + "", limit + "", offset + "", data);
        call.enqueue(new Callback<ApiObject<DocumentCategory>>() {
            @Override
            public void onResponse(Call<ApiObject<DocumentCategory>> call, Response<ApiObject<DocumentCategory>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetListDocumentCategorySuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetListDocumentCategoryError();
                    }
                } else {
                    callback.onGetListDocumentCategoryError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<DocumentCategory>> call, Throwable t) {
                callback.onGetListDocumentCategoryError();
            }
        });
    }

    public interface ApiCategoryListener {
        void onGetCategorySuccess(ArrayList<DocumentAreaCategory> categories);
    }

    public interface ApiListDocumentCategoryListener {
        void onGetListDocumentCategorySuccess(ArrayList<DocumentCategory> categories, int totalRecord);
        void onGetListDocumentCategoryError();
    }
}
