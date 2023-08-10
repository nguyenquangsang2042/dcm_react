package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDocumentInteractiveController extends ApiController {
    public ApiDocumentInteractiveController() {

    }

    public void getDocumentInteractive(int limit, int offset, ApiDocumentInteractiveListener callback) {
        Call<ApiObject<DocumentInteractive>> call = getApiRoute().getDocumentInteractive(limit + "", offset + "", CurrentUser.getInstance().getUser().getDefaultLanguageid() + "");
        call.enqueue(new Callback<ApiObject<DocumentInteractive>>() {
            @Override
            public void onResponse(Call<ApiObject<DocumentInteractive>> call, Response<ApiObject<DocumentInteractive>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetDocumentInteractiveSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetDocumentInteractiveError();
                    }
                } else {
                    callback.onGetDocumentInteractiveError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<DocumentInteractive>> call, Throwable t) {
                callback.onGetDocumentInteractiveError();
            }
        });
    }

    public interface ApiDocumentInteractiveListener {
        void onGetDocumentInteractiveSuccess(ArrayList<DocumentInteractive> documentInteractive, int totalRecord);

        void onGetDocumentInteractiveError();
    }
}
