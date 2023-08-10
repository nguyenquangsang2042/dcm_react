package com.vuthao.VNADCM.base.model.custom;

import com.vuthao.VNADCM.base.model.Status;
import com.vuthao.VNADCM.base.model.app.DocumentType;
import com.vuthao.VNADCM.base.model.app.Settings;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class MasterData extends Status {
    private ArrayList<com.vuthao.VNADCM.base.model.app.DocumentAreaCategory> DocumentAreaCategory;
    private ArrayList<com.vuthao.VNADCM.base.model.app.FavoriteFolder> FavoriteFolder;
    private ArrayList<com.vuthao.VNADCM.base.model.app.User> CurrentUser;
    private ArrayList<DocumentType> DocumentType;

    public MasterData() {
    }

    public ArrayList<com.vuthao.VNADCM.base.model.app.DocumentAreaCategory> getDocumentAreaCategory() {
        return DocumentAreaCategory;
    }

    public void setDocumentAreaCategory(ArrayList<com.vuthao.VNADCM.base.model.app.DocumentAreaCategory> documentAreaCategory) {
        DocumentAreaCategory = documentAreaCategory;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.app.FavoriteFolder> getFavoriteFolder() {
        return FavoriteFolder;
    }

    public void setFavoriteFolder(ArrayList<com.vuthao.VNADCM.base.model.app.FavoriteFolder> favoriteFolder) {
        FavoriteFolder = favoriteFolder;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.app.User> getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(ArrayList<com.vuthao.VNADCM.base.model.app.User> currentUser) {
        CurrentUser = currentUser;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.app.DocumentType> getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(ArrayList<com.vuthao.VNADCM.base.model.app.DocumentType> documentType) {
        DocumentType = documentType;
    }
}
