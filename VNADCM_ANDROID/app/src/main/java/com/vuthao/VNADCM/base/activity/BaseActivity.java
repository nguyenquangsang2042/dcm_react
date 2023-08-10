package com.vuthao.VNADCM.base.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.NetworkChangeReceiver;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.custom.DialogNoConnection;
import com.vuthao.VNADCM.databinding.ToastLayoutBinding;

public abstract class BaseActivity extends AppCompatActivity implements NetworkChangeReceiver.ReceiverCallback {
    public static BaseActivity sBaseActivity;
    private Dialog mProgressDialog;
    private Toast mToast;

    private SharedPreferencesController preferencesController;
    private NetworkChangeReceiver broadcastReceiver;
    private DialogNoConnection dialogNoConnection;
    private PermissionListener permissionListener;
    private static final int PERMISSIONS_REQUEST = 2251;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBaseActivity = this;

        preferencesController = new SharedPreferencesController(this);
    }

    @Override
    public void OnShowInfoNetWork(boolean b) {
        if (dialogNoConnection == null)
            dialogNoConnection = new DialogNoConnection(BaseActivity.this);
        if (b && !dialogNoConnection.isShowing()) dialogNoConnection.show();
        else dialogNoConnection.cancel();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void selectEditText(EditText editText) {
        editText.requestFocusFromTouch();
        editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
        editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
        editText.setSelection(editText.getText().toString().length());
    }

    public void checkUserPermission(PermissionListener permissionListener, String[] permissions) {
        this.permissionListener = permissionListener;
        ActivityCompat.requestPermissions(
                this,
                permissions,
                PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (!shouldShowRequestPermissionRationale(permissions[i]) && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (this.permissionListener != null) {
                            //permissionListener.OnNeverRequestPermission();
                            permissionListener.OnAcceptedAllPermission();
                            return;
                        }
                    } else {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if (this.permissionListener != null) {
                                permissionListener.OnCancelPermission();
                            }
                            return;
                        }
                    }
                }

                if (this.permissionListener != null) {
                    permissionListener.OnAcceptedAllPermission();
                }
            }
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(this, R.style.AlertDialogCustom);
            mProgressDialog.setContentView(R.layout.layout_progress_dialog);
            mProgressDialog.setCancelable(false);
        }
        try {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception ignored) {
            Log.d("ERR showProgressDialog", ignored.getMessage());
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception ignored) {
                Log.d("ERR hideProgressDialog", ignored.getMessage());
            }
        }
    }

    public void showToast(String str, @Nullable ToastClickListener listener) {
        if (mToast == null) {
            mToast = new Toast(this);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        ToastLayoutBinding binding = ToastLayoutBinding.inflate(getLayoutInflater(), findViewById(R.id.container), false);
        binding.tvTitle.setText(str);
        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.OnToastClicked();
        });
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(binding.getRoot());
        mToast.show();
    }

    public void showToast(String str, int yOffset, @Nullable ToastClickListener listener) {
        if (mToast == null) {
            mToast = new Toast(this);
            yOffset = Functions.share.convertDpToPixel(yOffset, sBaseActivity);
            mToast.setGravity(Gravity.BOTTOM, 0, yOffset);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        ToastLayoutBinding binding = ToastLayoutBinding.inflate(getLayoutInflater(), findViewById(R.id.container), false);
        binding.tvTitle.setText(str);
        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.OnToastClicked();
        });
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(binding.getRoot());
        mToast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new NetworkChangeReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            this.registerReceiver(broadcastReceiver, filter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) unregisterReceiver(broadcastReceiver);
        if (dialogNoConnection != null && dialogNoConnection.isShowing()) {
            dialogNoConnection.dismiss();
        }
    }

    public SharedPreferencesController getPreferencesController() {
        return preferencesController;
    }

    public interface PermissionListener {
        void OnAcceptedAllPermission();

        void OnCancelPermission();

        void OnNeverRequestPermission();
    }

    public interface ToastClickListener {
        void OnToastClicked();
    }
}
