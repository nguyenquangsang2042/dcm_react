package com.vuthao.VNADCM.comment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.comment.adapter.CommentAdapter;
import com.vuthao.VNADCM.comment.presenter.CommentPresenter;
import com.vuthao.VNADCM.databinding.ActivityCommentBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class CommentActivity extends BaseActivity implements View.OnClickListener, CommentPresenter.CommentListener, CommentAdapter.CommentListener, SwipeRefreshLayout.OnRefreshListener {
    private ActivityCommentBinding binding;
    private CommentAdapter adapter;
    private ArrayList<Comment> comments;
    private CommentPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        addLoadMore();
        presenter.getComments(Constants.mDataLimit, 0, false);

        binding.swipeRefresh.setOnRefreshListener(this);
        binding.imgBack.setOnClickListener(this);
    }

    private void init() {
        presenter = new CommentPresenter(this);
        comments = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        binding.lvItems.setLayoutManager(mLayoutManager);
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);
    }

    private void addLoadMore() {
        binding.lvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoadMore) {
                    if (mLayoutManager.findLastVisibleItemPosition() == comments.size() - 1) {
                        loadMore();
                        isLoadMore = false;
                    }
                }
            }
        });
    }

    private void loadMore() {
        this.comments.add(null);
        this.adapter.notifyItemInserted(this.comments.size() - 1);
        presenter.getComments(Constants.mDataLimit - 10, this.comments.size() - 1, true);
    }

    private void loadList() {
        if (comments.size() > 0) {
            adapter = new CommentAdapter(this, comments, this);
            binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapter));

            binding.shimmer.stopShimmerAnimation();
            binding.shimmer.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.VISIBLE);
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.VISIBLE);
            binding.lvItems.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Comment screen");
    }

    @Override
    protected void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            finish();
        }
    }

    @Override
    public void onGetCommentSuccess(ArrayList<Comment> comments, boolean isLoadMore, int totalRecord) {
        if (isLoadMore) {
            this.comments.remove(this.comments.size() - 1);
            this.adapter.notifyItemRemoved(this.comments.size() - 1);
            this.comments.addAll(comments);
            this.adapter.notifyDataSetChanged();
        } else {
            this.comments = comments;
            loadList();
        }

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }

        this.isLoadMore = this.comments.size() < totalRecord;
    }

    @Override
    public void onGetCommentError() {
        comments = presenter.getCommentsOffline();
        loadList();
    }

    @Override
    public void onItemClick(int pos) {
        ActivityController.share.goToDocumentDetail(this, comments.get(pos).getResourceUrl());
    }

    @Override
    public void onRefresh() {
        presenter.getComments(Constants.mDataLimit, 0, false);
    }
}