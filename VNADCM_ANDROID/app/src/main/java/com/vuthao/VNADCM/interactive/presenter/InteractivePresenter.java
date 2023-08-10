package com.vuthao.VNADCM.interactive.presenter;

import com.vuthao.VNADCM.base.api.ApiDocumentInteractiveController;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.base.realm.RealmDocumentInteractiveController;
import com.vuthao.VNADCM.base.realm.RealmNotifyController;

import java.util.ArrayList;

public class InteractivePresenter {
    private final InteractiveListener listener;
    private final ApiDocumentInteractiveController apiController;
    private final RealmDocumentInteractiveController realm;

    public InteractivePresenter(InteractiveListener listener){
        this.listener = listener;
        this.apiController = new ApiDocumentInteractiveController();
        this.realm = new RealmDocumentInteractiveController();
    }

    public void getDocumentInteractive(int limit, int offset, boolean isLoadMore){
        apiController.getDocumentInteractive(limit, offset, new ApiDocumentInteractiveController.ApiDocumentInteractiveListener() {
            @Override
            public void onGetDocumentInteractiveSuccess(ArrayList<DocumentInteractive> documentInteractives, int totalRecord) {
                realm.addItems(documentInteractives);
                listener.onInteractiveDocumentSuccess(documentInteractives, isLoadMore, totalRecord);
            }

            @Override
            public void onGetDocumentInteractiveError() {
                listener.onInteractiveDocumentError();
            }
        });
    }

    public ArrayList<DocumentInteractive> getAllDocumentInteractive() {
        ArrayList<DocumentInteractive> documentInteractives = realm.getAllItems();
        return documentInteractives;
    }

    public interface InteractiveListener{
        void onInteractiveDocumentSuccess(ArrayList<DocumentInteractive> documentInteractives, boolean isLoadMore, int totalRecord);
        void onInteractiveDocumentError();
    }
}
