package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.ApiObject;
import com.vuthao.VNADCM.base.model.app.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class ApiCommentController extends ApiController {

    public ApiCommentController() {
    }

    public void getComments(String data, String limit, String offset, ApiCommentListener callback) {
        Call<ApiObject<Comment>> call = getApiRoute().getComments(data, limit, offset);
        call.enqueue(new Callback<ApiObject<Comment>>() {
            @Override
            public void onResponse(Call<ApiObject<Comment>> call, Response<ApiObject<Comment>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetCommentSuccess(response.body().getData().getData(), response.body().getData().getMoreInfo().get(0).getTotalRecord());
                    } else {
                        callback.onGetCommentError();
                    }
                } else {
                    callback.onGetCommentError();
                }
            }

            @Override
            public void onFailure(Call<ApiObject<Comment>> call, Throwable t) {
                callback.onGetCommentError();
            }
        });
    }
    public interface ApiCommentListener {
        void onGetCommentSuccess(ArrayList<Comment> comments, int totalRecord);
        void onGetCommentError();
    }
}
