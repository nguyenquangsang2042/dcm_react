package com.vuthao.VNADCM.base.api;

import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.SearchTrend;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class ApiSearchController extends ApiController {

    public void getSearchTrend(ApiSearchListener callback) {
        Call<ApiList<SearchTrend>> call = getApiRoute().getSearchTrend(CurrentUser.getInstance().getUser().getLineManagermentId() + "", CurrentUser.getInstance().getUser().getLineManagermentId() + "");
        call.enqueue(new Callback<ApiList<SearchTrend>>() {
            @Override
            public void onResponse(Call<ApiList<SearchTrend>> call, Response<ApiList<SearchTrend>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        callback.onGetSearchTrendSuccess(response.body().getData());
                    } else {
                        callback.onGetSearchTrendSuccess(new ArrayList<>());
                    }
                } else {
                    callback.onGetSearchTrendSuccess(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiList<SearchTrend>> call, Throwable t) {
                callback.onGetSearchTrendSuccess(new ArrayList<>());
            }
        });
    }

    public interface ApiSearchListener {
        void onGetSearchTrendSuccess(ArrayList<SearchTrend> searchTrends);
    }
}
