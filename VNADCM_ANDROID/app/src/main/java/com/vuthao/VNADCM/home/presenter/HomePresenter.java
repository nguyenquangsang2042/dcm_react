package com.vuthao.VNADCM.home.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.api.ApiDocumentController;
import com.vuthao.VNADCM.base.api.ApiNotificationController;
import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentRecently;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.base.model.custom.HomeResource;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentController;
import com.vuthao.VNADCM.base.realm.RealmDocumentRecentlyController;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class HomePresenter {
    private final ApiDocumentController apiDocumentController;
    private final HomeListener listener;
    private final RealmDocumentController realmDocumentController;
    private final RealmDocumentRecentlyController realmDocumentRecentlyController;
    private final ApiDCMController apiDCMController;

    public HomePresenter(HomeListener listener) {
        this.listener = listener;
        this.apiDocumentController = new ApiDocumentController();
        this.realmDocumentRecentlyController = new RealmDocumentRecentlyController();
        this.realmDocumentController = new RealmDocumentController();
        this.apiDCMController = new ApiDCMController();
    }

    public ArrayList<Document> getDocumentRecently() {
        ArrayList<Document> documents = new ArrayList<>();
        ArrayList<DocumentRecently> documentRecentlies = realmDocumentRecentlyController.getAllItems();
        if (documentRecentlies.size() > 5) {
            documentRecentlies = (ArrayList<DocumentRecently>) documentRecentlies.stream().limit(5).collect(Collectors.toList());
        }

        Integer[] id = new Integer[documentRecentlies.size()];
        for (int i = 0; i < documentRecentlies.size(); i++) {
            id[i] = documentRecentlies.get(i).getID();
        }

        if (id.length > 0) {
            documents = realmDocumentController.getDocumentRecently(id);
        }

        return documents;
    }

    public ArrayList<Document> getDocumentsMostViewed() {
        return realmDocumentController.getDocumentsMostView();
    }

    public ArrayList<Document> getDocumentsNewest() {
        return realmDocumentController.getDocumentsNewest();
    }

    public ArrayList<Document> getDocumentsFavorite() {
        ArrayList<Document> documents = realmDocumentController.getDocumentsFavorite();
        if (documents != null && documents.size() > 5) {
            documents = (ArrayList<Document>) documents.stream().limit(5).collect(Collectors.toList());
        }
        return documents;
    }

    public void getResourcesHome(int limit, int offset) {
        apiDocumentController.getHomeResources(limit, offset, new ApiDocumentController.ApiHomeResourceListener() {
            @Override
            public void onGetHomeResourceSuccess(HomeResource homeResource) {
                modifiedData(homeResource);
                listener.onGetHomeResourceSuccess(homeResource);
            }

            @Override
            public void onGetHomeResourceError() {
                listener.onGetHomeResourceError();
            }
        });
    }

    private void modifiedData(HomeResource homeResource) {
        deleteData();
        if (homeResource.getViewDocumentNew() != null && homeResource.getViewDocumentNew().size() > 0) {
            ArrayList<Document> documents = homeResource.getViewDocumentNew();
            for (Document doc : documents) {
                Document d = realmDocumentController.getItemById(doc.getID());
                if (d != null) {
                    doc.setMostViewedL(d.isMostViewedL());
                    doc.setFavoriteL(d.isFavoriteL());
                    doc.setLastTimeView(d.getLastTimeView());
                    doc.setThumbnail(d.getThumbnail());
                }

                doc.setNewestL(true);
                realmDocumentController.addNewItem(doc);
            }

            documents.sort((document, t1) -> {
                int sComp = t1.getIssueDate().compareTo(document.getIssueDate());
                if (sComp != 0) {
                    return sComp;
                }
                return document.getTitle().compareTo(t1.getTitle());
            });
        }

        if (homeResource.getDocumentMostView() != null && homeResource.getDocumentMostView().size() > 0) {
            ArrayList<Document> documents = homeResource.getDocumentMostView();
            for (Document doc : documents) {
                Document d = realmDocumentController.getItemById(doc.getID());
                if (d != null) {
                    doc.setNewestL(d.isNewestL());
                    doc.setFavoriteL(d.isFavoriteL());
                    doc.setLastTimeView(d.getLastTimeView());
                    doc.setThumbnail(d.getThumbnail());
                }

                doc.setMostViewedL(true);
                realmDocumentController.addNewItem(doc);
            }

            documents.sort((document, t1) -> {
                int sComp = t1.getIssueDate().compareTo(document.getIssueDate());
                if (sComp != 0) {
                    return sComp;
                }
                return document.getTitle().compareTo(t1.getTitle());
            });
        }

        if (homeResource.getDocumentFavorite() != null && homeResource.getDocumentFavorite().size() > 0) {
            ArrayList<Document> documents = homeResource.getDocumentFavorite();
            for (Document doc : documents) {
                Document d = realmDocumentController.getItemById(doc.getID());
                if (d != null) {
                    doc.setNewestL(d.isNewestL());
                    doc.setMostViewedL(d.isMostViewedL());
                    doc.setLastTimeView(d.getLastTimeView());
                    doc.setThumbnail(d.getThumbnail());
                }

                doc.setFavoriteL(true);
                realmDocumentController.addNewItem(doc);
            }
        }
    }

    // Xóa data cũ đi để xử lý trường hợp item local có nhưng server đã xóa
    private void deleteData() {
        ArrayList<Document> documentRecentlies = getDocumentRecently();

        if (documentRecentlies.size() > 0) {
            Integer[] ids = new Integer[documentRecentlies.size()];
            for (int i = 0; i < documentRecentlies.size(); i++) {
                ids[i] = documentRecentlies.get(i).getID();
            }

            realmDocumentController.deleteAllWithOutRecently(ids);
            realmDocumentController.updateAllHomeResouces();
        }
    }

    public void getNotificationVisble() {
        new ApiNotificationController().getCountNotify(count -> listener.onNotificationVisble(count));
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

    public interface HomeListener {
        void onGetHomeResourceSuccess(HomeResource homeResource);

        void onRefreshSuccess();

        void onGetHomeResourceError();

        void onNotificationVisble(int count);
    }
}
