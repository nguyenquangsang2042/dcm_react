package com.vuthao.VNADCM.document;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;
import com.vuthao.VNADCM.databinding.LayoutProgressDownloadDialogBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Call;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 28/02/2023.
 */
public class DocumentDownload extends AsyncTask<String, Integer, Boolean> {
    private DocumentDetailWebActivity activity;
    private DocumentDownloadListener listener;
    private PowerManager.WakeLock mWakeLock;
    private Dialog mProgressDialog;
    private LayoutProgressDownloadDialogBinding binding;
    private long contentLength;
    private static final String TAG = DocumentDownload.class.getSimpleName();

    public DocumentDownload(DocumentDetailWebActivity activity, DocumentDownloadListener listener, long contentLength) {
        this.activity = activity;
        this.listener = listener;
        this.contentLength = contentLength;

        mProgressDialog = new Dialog(activity, R.style.AlertDialogCustom);
        binding = LayoutProgressDownloadDialogBinding.inflate(activity.getLayoutInflater());
        mProgressDialog.setContentView(binding.getRoot());
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        String size = Functions.share.getFormatFileSize(contentLength);
        binding.tvFileSize.setText(size);

        int width = Functions.share.getScreenWidth() - Functions.share.convertDpToPixel(30, activity);
        mProgressDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
        activity.hideProgressDialog();
    }

    @Override
    protected Boolean doInBackground(String... url) {
        try {
            PersistentCookieStore cookieStore = new PersistentCookieStore(DCMApplication.context);
            CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

            OkHttpClient.Builder client =
                    new OkHttpClient
                            .Builder();

            Call call = client
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build()
                    .newCall(new Request.Builder()
                            .url(url[0])
                            .get()
                            .build());

            Response response = call.execute();
            InputStream input = response.body().byteStream();

            String fileName = url[2];
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
            String folderName = "/VNADCM/" + url[1];
            String mineType = Functions.share.getMineTypeExtension(fileName);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileNameWithoutExtension);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mineType);
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + folderName);
                contentValues.put(MediaStore.Video.Media.TITLE, fileNameWithoutExtension);
                contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
                contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
                final ContentResolver resolver = activity.getContentResolver();
                Uri uri = null;

                final Uri contentUri = MediaStore.Files.getContentUri("external");
                uri = resolver.insert(contentUri, contentValues);
                ParcelFileDescriptor pfd;

                assert uri != null;
                pfd = activity.getContentResolver().openFileDescriptor(uri, "w");
                assert pfd != null;

                OutputStream output = new FileOutputStream(pfd.getFileDescriptor());

                byte[] data = new byte[4096];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return false;
                    }
                    total += count;
                    // publishing the progress....
                    // only if total length is known
                    if (contentLength > 0) {
                        publishProgress((int) (total * 100 / contentLength));
                    }

                    output.write(data, 0, count);
                }

                output.flush();
                output.close();

                contentValues.clear();
                contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
                activity.getContentResolver().update(uri, contentValues, null, null);
                output = resolver.openOutputStream(uri);
                return output != null;
            } else {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
                File myDir = new File(root + folderName);
                if (!myDir.exists()) {
                    boolean mkdir = myDir.mkdirs();
                    if (!mkdir) {
                        return false;
                    }
                }

                myDir = new File(myDir, fileName);

                OutputStream output = new FileOutputStream(myDir);

                byte[] data = new byte[4096];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return false;
                    }
                    total += count;
                    // publishing the progress....
                    // only if total length is known
                    if (contentLength > 0) {
                        publishProgress((int) (total * 100 / contentLength));
                    }

                    output.write(data, 0, count);
                }

                output.flush();
                output.close();

                return true;
            }
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);

        // if we get here, length is known, now set indeterminate to false
        binding.progress.setIndeterminate(false);
        binding.progress.setMax(100);
        binding.progress.setProgress(progress[0]);
        binding.tvPercent.setText(progress[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean res) {
        super.onPostExecute(res);
        mProgressDialog.dismiss();
        if (!res) {
            listener.onDownloadError();
        } else {
            listener.onDownloadSuccess();
        }
    }

    public interface DocumentDownloadListener {
        void onDownloadSuccess();

        void onDownloadError();
    }
}
