package com.vuthao.VNADCM.search.presenter;

import android.content.Context;

import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.SearchHistory;
import com.vuthao.VNADCM.base.model.app.SearchTrend;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmSearchHistoryController;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class SearchPresenter {
    private SearchListener listener;
    private final RealmSearchHistoryController realmSearchHistoryController;
    private ApiAuthController apiAuthController;
    private final ApiDCMController apiDCMController;

    public SearchPresenter(SearchListener listener) {
        this.listener = listener;
        this.realmSearchHistoryController = new RealmSearchHistoryController();
        this.apiDCMController = new ApiDCMController();
    }

    public SearchPresenter() {
        this.realmSearchHistoryController = new RealmSearchHistoryController();
        this.apiAuthController = new ApiAuthController();
        this.apiDCMController = new ApiDCMController();
    }

    public ArrayList<SearchTrend> getSearchTrend() {
        ArrayList<SearchTrend> retValue = new ArrayList<>();

        if (!Functions.isNullOrEmpty(CurrentUser.getInstance().getUser().getTopUsedKey())) {
            String[] searchTrend = CurrentUser.getInstance().getUser().getTopUsedKey().split(",");
            if (searchTrend.length > 0) {
                for (String value : searchTrend) {
                    SearchTrend s = new SearchTrend(value);
                    retValue.add(s);
                }
            }
        }
        return retValue;
    }

    public ArrayList<SearchHistory> getSearchHistories() {
        ArrayList<SearchHistory> searchHistories = realmSearchHistoryController.getAllItems();
        if (searchHistories.size() > 10) {
            searchHistories = (ArrayList<SearchHistory>) searchHistories.stream().limit(10).collect(Collectors.toList());
        }
        return searchHistories;
    }

    public void addKeyword(String keyword) {
        SearchHistory searchHistory = new SearchHistory(keyword, System.currentTimeMillis());
        realmSearchHistoryController.addItem(searchHistory);
    }

    public void removeKeyword(String keyword) {
        realmSearchHistoryController.deleteById(keyword);
    }

    public void getTokenWebView(TokenWebViewListener callback) {
        apiAuthController.getTokenWebView(new ApiAuthController.ApiTokenWebViewListener() {
            @Override
            public void onGetTokenSuccess(String token) {
                callback.onGetTokenSuccess(token);
            }

            @Override
            public void onGetTokenError() {
                callback.onGetTokenError();
            }
        });
    }

    public void refreshData(Context context) {
        apiDCMController.updateAllMasterData(context, new ApiDCMController.ApiDCMListener() {
            @Override
            public void onGetAllDataSuccess(MasterData masterData, String dateNow) {
                listener.onRefreshSuccess();
            }

            @Override
            public void onGetAllDataError() {
                listener.onRefreshError();
            }
        });
    }

    public interface SearchListener {
        void onRefreshSuccess();
        void onRefreshError();
    }

    public interface TokenWebViewListener {
        void onGetTokenSuccess(String token);
        void onGetTokenError();
    }
}
