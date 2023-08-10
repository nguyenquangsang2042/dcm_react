package com.vuthao.VNADCM.base.model.custom;

import com.vuthao.VNADCM.base.model.app.Document;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 17/02/2023.
 */
public class HomeResource {
    private ArrayList<Document> ViewDocumentNew;
    private ArrayList<Document> DocumentMostView;
    private ArrayList<Document> DocumentFavorite;

    public HomeResource() {
    }

    public ArrayList<Document> getViewDocumentNew() {
        return ViewDocumentNew;
    }

    public void setViewDocumentNew(ArrayList<Document> viewDocumentNew) {
        ViewDocumentNew = viewDocumentNew;
    }

    public ArrayList<Document> getDocumentMostView() {
        return DocumentMostView;
    }

    public void setDocumentMostView(ArrayList<Document> documentMostView) {
        DocumentMostView = documentMostView;
    }

    public ArrayList<Document> getDocumentFavorite() {
        return DocumentFavorite;
    }

    public void setDocumentFavorite(ArrayList<Document> documentFavorite) {
        DocumentFavorite = documentFavorite;
    }
}
