package com.vuthao.VNADCM.favorite.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.api.ApiFavoriteController;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentFavoriteController;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class FavoritePresenter {
    private FavoriteListener listener;
    private final ApiFavoriteController apiFavoriteController;
    private final RealmDocumentFavoriteController realmDocumentFavoriteController;
    private ApiDCMController apiDCMController;

    public FavoritePresenter(FavoriteListener listener) {
        this.listener = listener;
        this.apiFavoriteController = new ApiFavoriteController();
        this.realmDocumentFavoriteController = new RealmDocumentFavoriteController();
        this.apiDCMController = new ApiDCMController();
    }

    public FavoritePresenter() {
        this.apiFavoriteController = new ApiFavoriteController();
        this.realmDocumentFavoriteController = new RealmDocumentFavoriteController();
    }

    public void getDocumentFavorite(String id, int limit, int offset, boolean isLoadMore, DocumentFavoriteListener callback) {
        apiFavoriteController.getDocumentFavoriteById(id, limit, offset, new ApiFavoriteController.ApiDocumentFavoriteListener() {
            @Override
            public void onGetDocumentFavoriteSuccess(ArrayList<DocumentFavorite> documentFavorites, int totalRecord) {
                if (offset == 0) {
                    realmDocumentFavoriteController.deleteAll();
                }

                realmDocumentFavoriteController.addItems(documentFavorites);
                callback.onGetDocumentFavoriteSuccess(documentFavorites, isLoadMore, totalRecord);
            }

            @Override
            public void onGetDocumentFavoriteError() {
                callback.onGetDocumentFavoriteError();
            }
        });
    }

    public ArrayList<DocumentFavorite> getDocumentFavoriteOffline(String folderId) {
        return realmDocumentFavoriteController.getItemsByFolderId(folderId);
    }

    public void updateData(Context context) {
        apiDCMController.updateAllMasterData(context, new ApiDCMController.ApiDCMListener() {
            @Override
            public void onGetAllDataSuccess(MasterData masterData, String dateNow) {
                listener.onRefreshSuccess();
            }

            @Override
            public void onGetAllDataError() {
                listener.onRefreshSuccess();
            }
        });
    }

    public interface DocumentFavoriteListener {
        void onGetDocumentFavoriteSuccess(ArrayList<DocumentFavorite> favorites, boolean isLoadMore, int totalRecord);

        void onGetDocumentFavoriteError();
    }

    public interface FavoriteListener {
        void onRefreshSuccess();
    }
}
