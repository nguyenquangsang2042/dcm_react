package com.vuthao.VNADCM.base;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

@GlideModule
public class ImageLoader extends AppGlideModule {
    private static ImageLoader instance;

    public ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void loadGifImage(Context context, String url, ImageView img) {
        GlideApp.with(context)
                .load(url)
                .into(img);
    }

    public void loadImageUser(final Context activity, final String url, final ImageView img) {
        if (url == null || url.isEmpty()) {
            loadImageNoOne(activity, img);
            return;
        }

        GlideApp.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .disallowHardwareConfig()
                .placeholder(R.drawable.icon_avatar64)
                .error(R.drawable.icon_avatar64)
                .override(200, 200)
                .centerCrop()
                .into(img)
                .waitForLayout();
    }

    public void loadImageDocument(final Context activity, final String url, final ImageView img) {
        if (url == null || url.isEmpty()) {
            loadImageNoOne(activity, img);
            return;
        }

        GlideApp.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .disallowHardwareConfig()
                .placeholder(R.drawable.icon_document_default)
                .error(R.drawable.icon_document_default)
                .override(200, 200)
                .centerCrop()
                .into(img)
                .waitForLayout();
    }

    public void loadImageCategory(final Context activity, final String url, final ImageView img) {
        if (url == null || url.isEmpty()) {
            loadImageNoOne(activity, img);
            return;
        }

        GlideApp.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .disallowHardwareConfig()
                .placeholder(R.drawable.icon_thumbnail_category_default)
                .error(R.drawable.icon_thumbnail_category_default)
                .override(120, 120)
                .centerCrop()
                .into(img)
                .waitForLayout();
    }

    public void loadImageResource(final Activity activity, final int resourceId, final ImageView img) {
        GlideApp.with(activity)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .disallowHardwareConfig()
                .override(200, 200)
                .centerCrop()
                .into(img)
                .waitForLayout();
    }

    private void loadImageNoOne(Context activity, ImageView img) {
        GlideApp.with(activity)
                .load(R.drawable.icon_avatar64)
                .into(img);
    }

    public void storageImage(final Activity activity, final String url) {
        GlideApp.with(activity)
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                        boolean success = false;
                        try {
                            String root = Environment.getExternalStorageDirectory().toString();
                            File myDir = new File(root + "/VNADCM");
                            if (!myDir.exists()) {
                                myDir.mkdirs();
                            }
                            String fileName = System.currentTimeMillis() + "s.png";
                            myDir = new File(myDir, fileName);
                            FileOutputStream out = new FileOutputStream(myDir);

                            // TODO didn't check
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.flush();
                            out.close();
                            success = true;
                            ContentValues values = new ContentValues();

                            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                            values.put(MediaStore.MediaColumns.DATA, myDir.getAbsolutePath());

                            activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (success) {
                            Functions.Toast(activity, "Ảnh đã được lưu thành công vào thư mục VNADCM !");
                        } else {
                            Functions.Toast(activity, "Lưu ảnh thất bại !!!");
                        }
                    }
                });
    }
}
