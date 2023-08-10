package com.vuthao.VNADCM.interactive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.ApiDocumentInteractiveController;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.databinding.ActivityInteractiveBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.interactive.adapter.InteractiveAdapter;
import com.vuthao.VNADCM.interactive.presenter.InteractivePresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class InteractiveActivity extends BaseActivity implements View.OnClickListener, InteractivePresenter.InteractiveListener, InteractiveAdapter.DocumentInteractiveListener, SwipeRefreshLayout.OnRefreshListener {
    private ActivityInteractiveBinding binding;
    private InteractivePresenter presenter;
    private ArrayList<DocumentInteractive> documentInteractives;
    private InteractiveAdapter adapterInteractive;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityInteractiveBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        addLoadMore();
        presenter.getDocumentInteractive(Constants.mDataLimit, 0, false);

        binding.imgBack.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    private void addLoadMore() {
        binding.lvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading && mLayoutManager.findLastVisibleItemPosition() == documentInteractives.size() - 1) {
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        isLoading = false;

        documentInteractives.add(null);
        adapterInteractive.notifyItemInserted(documentInteractives.size() - 1);
        presenter.getDocumentInteractive(Constants.mDataLimit - 10, documentInteractives.size() - 1, true);
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        documentInteractives = new ArrayList<>();

        binding.lvItems.setLayoutManager(mLayoutManager);
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);

        presenter = new InteractivePresenter(this);
    }

    private void loadList() {
        if (this.documentInteractives.size() > 0) {
            if (adapterInteractive == null) {
                adapterInteractive = new InteractiveAdapter(this, this.documentInteractives, this);
                binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapterInteractive));
            } else {
                adapterInteractive.notifyDataSetChanged();
            }

            binding.lnNodata.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.VISIBLE);
        } else {
            binding.lvItems.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.VISIBLE);
        }

        binding.shimmer.stopShimmerAnimation();
        binding.shimmer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Interactive document screen");
    }

    @Override
    protected void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onInteractiveDocumentSuccess(ArrayList<DocumentInteractive> documentInteractives, boolean isLoadMore, int totalRecord) {
        if (isLoadMore) {
            this.documentInteractives.remove(this.documentInteractives.size() - 1);
            this.adapterInteractive.notifyItemRemoved(this.documentInteractives.size() - 1);
            this.documentInteractives.addAll(documentInteractives);
            this.adapterInteractive.notifyDataSetChanged();
        } else {
            this.documentInteractives.clear();
            this.documentInteractives.addAll(documentInteractives);
            loadList();
        }

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }

        this.isLoading = this.documentInteractives.size() < totalRecord;
    }

    @Override
    public void onInteractiveDocumentError() {
        documentInteractives = presenter.getAllDocumentInteractive();
        loadList();
    }

    @Override
    public void onItemClick(int pos) {
        ActivityController.share.goToDocumentDetail(this, documentInteractives.get(pos).getResourceUrl());
    }

    @Override
    public void onRefresh() {
        presenter.getDocumentInteractive(Constants.mDataLimit, 0, false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }
}