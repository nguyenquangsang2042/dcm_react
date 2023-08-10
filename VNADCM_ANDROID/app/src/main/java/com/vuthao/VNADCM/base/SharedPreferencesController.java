package com.vuthao.VNADCM.base;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.vuthao.VNADCM.R;

public class SharedPreferencesController {
    private static SharedPreferences sharedPreferences;

    public SharedPreferencesController(@NonNull Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(Functions.share.encodeBase64(context.getResources().getString(R.string.app_name)), Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("Username"), Functions.share.encodeBase64(username));
        editor.apply();
    }

    public void savePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("Password"), Functions.share.encodeBase64(password));
        editor.apply();
    }

    public void saveUser(String user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("User"), Functions.share.encodeBase64(user));
        editor.apply();
    }

    public void saveUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("UserId"), Functions.share.encodeBase64(userId));
        editor.apply();
    }

    public void saveLocaleId(String localeId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("LocaleId"), Functions.share.encodeBase64(localeId));
        editor.apply();
    }

    public void saveModified(String modified) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Functions.share.encodeBase64("Modified"), Functions.share.encodeBase64(modified));
        editor.apply();
    }

    public String getUsername() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("Username"), ""));
    }

    public String getPassword() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("Password"), ""));
    }

    public String getUserId() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("UserId"), ""));
    }

    public String getLocaleId() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("LocaleId"), ""));
    }

    public String getModified() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("Modified"), ""));
    }

    public String getUser() {
        return Functions.share.decodeBase64(sharedPreferences.getString(Functions.share.encodeBase64("User"), ""));
    }

    public void getUserLogin(UserLoginListenter callback) {
        String s = getUserId();
        callback.onLoaded(!s.isEmpty() ? s : "");
    }

    public void deleteAll() {
        sharedPreferences.edit().clear().apply();
    }

    public interface UserLoginListenter {
        void onLoaded(String userId);
    }
}
