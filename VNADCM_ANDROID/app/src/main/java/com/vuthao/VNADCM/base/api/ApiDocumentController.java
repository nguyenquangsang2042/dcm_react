package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.custom.HomeResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class ApiDocumentController extends ApiController {

    public ApiDocumentController() {
    }
    public void getHTML(ApiHTMLListener callback,String data,int langId)
    {
        Call<ApiObject<String>> call = getApiRouteToken().getHTML(langId,langId,data);
        call.enqueue(new Callback<ApiObject<String>>() {
            @Override
            public void onResponse(Call<ApiObject<String>> call, Response<ApiObject<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetHTMLSuccess(response.body().getData());
                    } else {
                        callback.onGetHTMLError("fail");
                    }
                } else {
                    callback.onGetHTMLError("fail");
                }
            }

            @Override
            public void onFailure(Call<ApiObject<String>> call, Throwable t) {
                callback.onGetHTMLError(t.getMessage());
            }
        });
    }

    public void getDocumentById(int id, ApiDocumentListener callback) {
        Call<ApiList<Document>> call = getApiRouteToken().getDocumentById(id + "");
        call.enqueue(new Callback<ApiList<Document>>() {
            @Override
            public void onResponse(Call<ApiList<Document>> call, Response<ApiList<Document>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetDocumentSuccess(response.body().getData());
                    } else {
                        callback.onGetDocumentError();
                    }
                } else {
                    callback.onGetDocumentError();
                }
            }

            @Override
            public void onFailure(Call<ApiList<Document>> call, Throwable t) {
                callback.onGetDocumentError();
            }
        });
    }

    public void getHomeResources(int limit, int offset, ApiHomeResourceListener callback) {
        Call<ApiObject<HomeResource>> call = getApiRouteToken().getHomeResources(limit + "",  offset + "");
        call.enqueue(new Callback<ApiObject<HomeResource>>() {
            @Override
            public void onResponse(Call<ApiObject<HomeResource>> call, Response<ApiObject<HomeResource>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetHomeResourceSuccess(response.body().getData());
                    } else {
                        callback.onGetHomeResourceError();
                    }
                } else {
                    callback.onGetHomeResourceError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<HomeResource>> call, Throwable t) {
                callback.onGetHomeResourceError();
            }
        });
    }

    public interface ApiHTMLListener {
        void onGetHTMLSuccess(String html);

        void onGetHTMLError(String err);
    }
    public interface ApiDocumentListener {
        void onGetDocumentSuccess(ArrayList<Document> documents);

        void onGetDocumentError();
    }

    public interface ApiHomeResourceListener {
        void onGetHomeResourceSuccess(HomeResource homeResource);
        void onGetHomeResourceError();
    }
}
