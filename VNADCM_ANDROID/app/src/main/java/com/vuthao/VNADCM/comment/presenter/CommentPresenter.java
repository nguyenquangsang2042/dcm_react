package com.vuthao.VNADCM.comment.presenter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.api.ApiCommentController;
import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.realm.RealmCommentController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class CommentPresenter {
    public CommentListener listener;
    private final ApiCommentController apiCommentController;
    private final RealmCommentController realmCommentController;

    public interface CommentListener {
        void onGetCommentSuccess(ArrayList<Comment> comments, boolean isLoadMore, int totalRecord);

        void onGetCommentError();
    }

    public CommentPresenter(CommentListener listener) {
        this.listener = listener;
        this.apiCommentController = new ApiCommentController();
        this.realmCommentController = new RealmCommentController();
    }

    public void getComments(int limit, int offset, boolean isLoadMore) {
        Map<String, Map<String, String>> hashMap = new HashMap<>();
        Map<String, String> h = new HashMap<>();
        h.put("CommentTitle", null);
        h.put("CommentStorageCode", null);
        h.put("CommentVersion", null);
        h.put("CommentDate", null);
        h.put("CommentIsApproved", null);
        h.put("CommentStatus", null);
        hashMap.put("Parameters", h);

        String data = new GsonBuilder().serializeNulls().create().toJson(hashMap);
        apiCommentController.getComments(data, limit + "", offset + "", new ApiCommentController.ApiCommentListener() {
            @Override
            public void onGetCommentSuccess(ArrayList<Comment> comments, int totalRecord) {
                realmCommentController.addItems(comments);
                listener.onGetCommentSuccess(comments, isLoadMore, totalRecord);
            }

            @Override
            public void onGetCommentError() {
                listener.onGetCommentError();
            }
        });
    }

    public static int getColorById(Context context, Comment comment) {
        if (comment.getStatus() == -1) {
            return ContextCompat.getColor(context, R.color.clRed1);
        } else if (comment.getIsApproved() == 0) {
            return ContextCompat.getColor(context, R.color.clBlue3);
        } else if (comment.getIsApproved() == 1) {
            return ContextCompat.getColor(context, R.color.clGreen);
        } else {
            return ContextCompat.getColor(context, R.color.clRed1);
        }
    }

    public static String getTitleById(Context context, Comment comment) {
        if (comment.getStatus() == -1) {
            return context.getString(R.string.status_delete);
        } else if (comment.getIsApproved() == 0) {
            return context.getString(R.string.status_approving);
        } else if (comment.getIsApproved() == 1) {
            return context.getString(R.string.status_approved);
        } else {
            return context.getString(R.string.status_refuse);
        }
    }

    public ArrayList<Comment> getCommentsOffline() {
        return realmCommentController.getAllItems();
    }
}
