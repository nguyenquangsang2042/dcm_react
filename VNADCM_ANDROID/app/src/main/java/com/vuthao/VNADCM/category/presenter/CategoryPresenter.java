package com.vuthao.VNADCM.category.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.api.ApiCategoryController;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentCategoryController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class CategoryPresenter {
    private final ApiCategoryController apiCategoryController;
    private final RealmCategoryController realmCategoryController;
    private final RealmDocumentCategoryController realmDocumentCategoryController;
    private final ApiDCMController apiDCMController;
    private CategoryListener listener;

    public interface CategoryListener {
        void onGetCategoriesSuccess(ArrayList<DocumentAreaCategory> categories);
        void onRefreshSuccess();
    }

    public interface DocumentCategoryListener {
        void onGetDocumentCategoriesSuccess(ArrayList<DocumentCategory> categories, boolean isLoadMore, int totalRecord);

        void onGetDocumentCategoriesError();
    }

    public CategoryPresenter(CategoryListener listener) {
        this.listener = listener;
        this.apiCategoryController = new ApiCategoryController();
        this.realmCategoryController = new RealmCategoryController();
        this.realmDocumentCategoryController = new RealmDocumentCategoryController();
        this.apiDCMController = new ApiDCMController();
    }

    public CategoryPresenter() {
        this.apiCategoryController = new ApiCategoryController();
        this.realmCategoryController = new RealmCategoryController();
        this.realmDocumentCategoryController = new RealmDocumentCategoryController();
        this.apiDCMController = new ApiDCMController();
    }

    public ArrayList<DocumentAreaCategory> getParentCategories() {
        return realmCategoryController.getParentItems();
    }

    public void getListDocumentCategory(int areaCategoryId, int limt, int offset, boolean isLoadMore, DocumentCategoryListener callback) {
        Map<String, Object> hashMap = new HashMap<>();
        Map<String, Object> h = new HashMap<>();
        h.put("AreaCategoryId", areaCategoryId);
        h.put("DocumentGroupId", "1");
        h.put("Title", "");
        h.put("Code", "");
        h.put("StorageCode", "");
        h.put("Int2", "");
        hashMap.put("Parameters", new Gson().toJson(h));

        String data = new Gson().toJson(hashMap);
        apiCategoryController.getListDocumentCategory(limt, offset, data, new ApiCategoryController.ApiListDocumentCategoryListener() {
            @Override
            public void onGetListDocumentCategorySuccess(ArrayList<DocumentCategory> categories, int totalRecord) {
                if (offset == 0) {
                    realmDocumentCategoryController.deleteAll();
                }

                realmDocumentCategoryController.addItems(categories);
                callback.onGetDocumentCategoriesSuccess(categories, isLoadMore, totalRecord);
            }

            @Override
            public void onGetListDocumentCategoryError() {
                callback.onGetDocumentCategoriesError();
            }
        });
    }

    public ArrayList<DocumentCategory> getDocmentCategoryOffline(int categoryId) {
        return realmDocumentCategoryController.getItemsAreaCategoryId(categoryId);
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
}
