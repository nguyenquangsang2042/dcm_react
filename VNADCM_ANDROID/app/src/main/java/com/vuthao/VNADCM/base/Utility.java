package com.vuthao.VNADCM.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.vuthao.VNADCM.BuildConfig;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;
import com.vuthao.VNADCM.base.model.custom.DeviceInfo;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.base.realm.RealmCommentController;
import com.vuthao.VNADCM.base.realm.RealmConfigureNotificationController;
import com.vuthao.VNADCM.base.realm.RealmDocumentCategoryController;
import com.vuthao.VNADCM.base.realm.RealmDocumentController;
import com.vuthao.VNADCM.base.realm.RealmDocumentFavoriteController;
import com.vuthao.VNADCM.base.realm.RealmDocumentFileController;
import com.vuthao.VNADCM.base.realm.RealmDocumentRecentlyController;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;
import com.vuthao.VNADCM.base.realm.RealmNotifyController;
import com.vuthao.VNADCM.base.realm.RealmSearchHistoryController;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Utility {
    public static Utility share = new Utility();

    public void showAlertWithOnlyOK(@NonNull String message, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, null, activity.getResources().getString(R.string.ok), null, activity, okListener);
    }

    public void showAlertWithOnlyOK(@NonNull String message, @NonNull Activity activity) {
        showAlertWithOKCancel(message, null, activity.getResources().getString(R.string.ok), null, activity, null);
    }

    public void showAlertWithOnlyOK(@NonNull String message, String ok, @NonNull Activity activity) {
        showAlertWithOKCancel(message, null, ok, null, activity, null);
    }

    public void showAlertWithOnlyOK(@NonNull String message, String ok, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, "", ok, null, activity, okListener);
    }

    public void showAlertWithOnlyOK(@NonNull String message, String title, String textOk, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, title, textOk, null, activity, okListener);
    }

    public void showAlertWithOKCancel(@NonNull String message, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, null, activity.getResources().getString(R.string.ok), activity.getResources().getString(R.string.cancel), activity, okListener);
    }

    public void showAlertWithOKCancel(@NonNull String message, String textCancel, String textOk, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, null, textOk, textCancel, activity, okListener);
    }

    public void showAlertWithYesNo(@NonNull String message, @NonNull Activity activity, final OkListener okListener) {
        showAlertWithOKCancel(message, null, "Yes", "No", activity, okListener);
    }

    public void showForceUpdateDialog(@NonNull String message, String title, String textOK, String textCancel, @NonNull Activity activity, final OkListener okListener) {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);

        if (title != null) {
            activity.setTitle(title);
        }
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(textOK, (dialog, which) -> {
            if (okListener != null) okListener.onOkListener();
            else dialog.cancel();
        });

        if (textCancel != null)
            alertDialog.setNegativeButton(textCancel, (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    public void showAlertWithOKCancel(@NonNull String message, String title, String textOK, String textCancel, @NonNull Activity activity, final OkListener okListener) {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);

        if (title != null) {
            activity.setTitle(title);
        }
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(textOK, (dialog, which) -> {
            if (okListener != null) okListener.onOkListener();
            else dialog.cancel();
        });

        if (textCancel != null)
            alertDialog.setNegativeButton(textCancel, (dialog, which) -> dialog.cancel());

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void showAlertWithOKCancel(@NonNull String message, @NonNull Activity activity, final CancelLisenter cancelLisenter, final OkListener okListener) {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);

        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(activity.getString(R.string.ok), (dialog, which) -> {
            if (okListener != null) okListener.onOkListener();
            else dialog.cancel();
        });

        alertDialog.setNegativeButton(activity.getString(R.string.cancel), (dialog, which) -> {
            if (cancelLisenter != null) cancelLisenter.onCancelLisenter();
            else dialog.cancel();
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    
    public void hideKeyboard(Context context, View view) {
        if (view == null) return;
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public boolean compareVersion(String currentVer, String newVer) {
        if (!currentVer.equals("") && !newVer.equals("")) {
            int current = Integer.parseInt(currentVer.replaceAll("\\.", ""));
            int newVersion = Integer.parseInt(newVer.replaceAll("\\.", ""));
            return newVersion > current;
        }
        return false;
    }

    public void showKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void onFocusChange(View v, boolean hasFocus, Context context) {
        if (v instanceof EditText) {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            }
        }
    }

    public void scrollToView(final NestedScrollView scrollView, final View view) {
        scrollView.post(() -> scrollView.scrollTo(0, view.getBottom()));
    }

    public static void avoidDoubleClicks(final View view) {
        final long DELAY_IN_MS = 900;
        if (!view.isClickable()) {
            return;
        }
        view.setClickable(false);
        view.postDelayed(() -> view.setClickable(true), DELAY_IN_MS);
    }

    public String getCurrentVersion(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(activity.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        assert pInfo != null;
        return pInfo.versionName;
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public void resetData(Context context) {
        // Deleting the important things from this device
        deleteSharePreferences(context);
        deleteImportantRealmData();
    }

    public void deleteSharePreferences(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(DCMApplication.context);
        cookieStore.removeAll();

        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(context);
        sharedPreferencesController.deleteAll();
    }

    public void deleteImportantRealmData() {
        new RealmCategoryController().deleteAll();
        new RealmFavoriteController().deleteAll();
        new RealmDocumentCategoryController().deleteAll();
        new RealmDocumentFavoriteController().deleteAll();
        new RealmNotifyController().deleteAll();
        new RealmCommentController().deleteAll();
        new RealmSearchHistoryController().deleteAll();
        new RealmDocumentRecentlyController().deleteAll();
        new RealmDocumentController().deleteAll();
        new RealmConfigureNotificationController().deleteAll();
        new RealmDocumentFileController().deleteAll();
    }

    @SuppressLint("HardwareIds")
    public DeviceInfo genDeviceInfo() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAppVersion(BuildConfig.VERSION_NAME);
        deviceInfo.setDeviceId(Settings.Secure.getString(BaseActivity.sBaseActivity.getContentResolver(), Settings.Secure.ANDROID_ID));
        deviceInfo.setDeviceModel(Build.MODEL);
        deviceInfo.setDeviceName(Build.MANUFACTURER);
        deviceInfo.setDeviceOS(1);
        deviceInfo.setDeviceOSVersion(String.valueOf(Build.VERSION.SDK_INT));
        deviceInfo.setDevicePushToken(Constants.mDeviceToken);
        return deviceInfo;
    }

    public interface OkListener {
        void onOkListener();
    }

    public interface CancelLisenter {
        void onCancelLisenter();
    }
}
