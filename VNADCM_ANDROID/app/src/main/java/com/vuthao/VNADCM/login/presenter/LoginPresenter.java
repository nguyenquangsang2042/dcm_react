package com.vuthao.VNADCM.login.presenter;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.Crypter;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.DocumentType;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.model.app.User;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentTypeController;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;
import com.vuthao.VNADCM.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nhum Lê Sơn Thạch on 09/02/2023.
 */
public class LoginPresenter {
    private final LoginListener listener;
    private final ApiAuthController apiAuthController;
    private final ApiDCMController apiDCMController;
    private final SharedPreferencesController sharedPreferencesController;

    public LoginPresenter(LoginActivity activity, LoginListener listener) {
        this.listener = listener;

        apiAuthController = new ApiAuthController();
        apiDCMController = new ApiDCMController();
        sharedPreferencesController = new SharedPreferencesController(activity);
    }

    public interface LoginListener {
        void onAuthSuccess();

        void onAuthError(String error);

        void onLoginSuccess();

        void onLoginError(String error);

        void onGetAllMasterDataSuccess();

        void onGetAllMasterDataError();
    }

    public void auth(String username, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Username", username);
        hashMap.put("Password", password);

        String data = new Gson().toJson(hashMap);
        data = Crypter.encrypt(data);

        apiAuthController.auth(data, new ApiAuthController.ApiAuthListener() {
            @Override
            public void onAuthSuccess() {
                sharedPreferencesController.saveUsername(username);
                sharedPreferencesController.savePassword(password);
                listener.onAuthSuccess();
            }

            @Override
            public void onAuthError(String error) {
                listener.onAuthError(error);
            }
        });
    }

    public void getCurrentUserInfo() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("DeviceInfo", new Gson().toJson(Utility.share.genDeviceInfo()));
        String data = new Gson().toJson(hashMap);
        apiAuthController.getUserInfo(data, new ApiAuthController.ApiAuthUserListener() {
            @Override
            public void onAuthUserSuccess(User user) {
                CurrentUser.getInstance().setUser(user);
                sharedPreferencesController.saveUser(new Gson().toJson(user));
                sharedPreferencesController.saveUserId(user.getID());
                sharedPreferencesController.saveLocaleId(user.getDefaultLanguageid() + "");
                listener.onLoginSuccess();
            }

            @Override
            public void onAuthUserError(String error) {
                listener.onLoginError(error);
            }
        });
    }

    public void getAllMasterData(boolean isGetAll) {
        String modified = isGetAll ? "" : sharedPreferencesController.getModified();
        apiDCMController.getAllMasterData(modified, new ApiDCMController.ApiDCMListener() {
            @Override
            public void onGetAllDataSuccess(MasterData masterData, String dateNow) {
                listener.onGetAllMasterDataSuccess();
                saveDataToRealm(masterData, dateNow);
            }

            @Override
            public void onGetAllDataError() {
                listener.onGetAllMasterDataError();
            }
        });
    }

    private void saveDataToRealm(MasterData masterData, String dateNow) {
        sharedPreferencesController.saveModified(dateNow);
        saveCategory(masterData.getDocumentAreaCategory());
        saveFavorite(masterData.getFavoriteFolder());
        saveDocumentType(masterData.getDocumentType());
    }

    private void saveDocumentType(ArrayList<DocumentType> documentType) {
        RealmDocumentTypeController realmDocumentTypeController = new RealmDocumentTypeController();
        realmDocumentTypeController.deleteAll();
        realmDocumentTypeController.addNewItems(documentType);
    }

    private void saveFavorite(ArrayList<FavoriteFolder> favoriteFolder) {
        RealmFavoriteController realmFavoriteController = new RealmFavoriteController();
        realmFavoriteController.deleteAll();
        realmFavoriteController.addItems(favoriteFolder);
    }

    private void saveCategory(ArrayList<DocumentAreaCategory> documentAreaCategory) {
        RealmCategoryController realmCategoryController = new RealmCategoryController();
        realmCategoryController.deleteAll();
        realmCategoryController.addItems(documentAreaCategory);
    }
}
