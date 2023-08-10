package com.vuthao.VNADCM.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.event.EventChange;
import com.vuthao.VNADCM.base.event.EventDispatcher;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.databinding.ActivityNotificationBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.notification.adapter.NotificationAdapter;
import com.vuthao.VNADCM.notification.presenter.NotificationPresenter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class NotificationActivity extends BaseActivity implements View.OnClickListener, NotificationPresenter.NotificationListener, NotificationAdapter.NotificationListener, SwipeRefreshLayout.OnRefreshListener {
    private ActivityNotificationBinding binding;
    private NotificationPresenter presenter;
    private NotificationAdapter adapter;
    private ArrayList<Notify> notifies;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading;
    private int mTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        addLoadMore();

        presenter.getNotifies(0, Constants.mDataLimit, false);

        binding.imgBack.setOnClickListener(this);
        binding.tvAll.setOnClickListener(this);
        binding.tvUnRead.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    private void init() {
        presenter = new NotificationPresenter(this);
        notifies = new ArrayList<>();
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
                if (isLoading) {
                    if (mLayoutManager.findLastVisibleItemPosition() == notifies.size() - 1) {
                        loadMore();
                        isLoading = false;
                    }
                }
            }
        });
    }

    private void loadMore() {
        notifies.add(null);
        adapter.notifyItemInserted(notifies.size() - 1);
        if (mTab == 0) {
            presenter.getNotifies(notifies.size() - 1, Constants.mDataLimit - 10, true);
        } else {
            presenter.getUnReadNotifies(notifies.size() - 1, Constants.mDataLimit - 10, true);
        }
    }

    private void setTabColor() {
        if (mTab == 0) {
            binding.tvAll.setTextColor(ContextCompat.getColor(this, R.color.clPrimary));
            binding.tvAll.setTypeface(Typeface.DEFAULT_BOLD);
            binding.tvAll.setBackgroundResource(R.drawable.bg_oval_white);

            binding.tvUnRead.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvUnRead.setTypeface(Typeface.DEFAULT);
            binding.tvUnRead.setBackground(null);
        } else {
            binding.tvUnRead.setTextColor(ContextCompat.getColor(this, R.color.clPrimary));
            binding.tvUnRead.setTypeface(Typeface.DEFAULT_BOLD);
            binding.tvUnRead.setBackgroundResource(R.drawable.bg_oval_white);

            binding.tvAll.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.tvAll.setTypeface(Typeface.DEFAULT);
            binding.tvAll.setBackground(null);
        }
    }

    private void loadList() {
        if (notifies.size() > 0) {
            if (adapter == null) {
                adapter = new NotificationAdapter(getApplicationContext(), notifies, this);
                binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapter));
            } else {
                adapter.notifyDataSetChanged();
                binding.lvItems.scrollToPosition(0);
            }

            binding.shimmer.stopShimmerAnimation();
            binding.lvItems.setVisibility(View.VISIBLE);
            binding.lnNodata.setVisibility(View.GONE);
            binding.shimmer.setVisibility(View.GONE);
        } else {
            binding.lvItems.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.VISIBLE);
            binding.shimmer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            onBackPressed();
        } else if (v == binding.tvAll) {
            mTab = 0;
            setTabColor();
            isLoading = false;
            binding.shimmer.startShimmerAnimation();
            binding.shimmer.setVisibility(View.VISIBLE);
            presenter.getNotifies(0, Constants.mDataLimit, isLoading);
        } else if (v == binding.tvUnRead) {
            mTab = 1;
            setTabColor();
            isLoading = false;
            binding.shimmer.startShimmerAnimation();
            binding.shimmer.setVisibility(View.VISIBLE);
            presenter.getUnReadNotifies(0, Constants.mDataLimit, isLoading);
        }
    }

    @Override
    public void onGetNotificationSuccess(ArrayList<Notify> notifies, boolean isLoadMore, int totalRecord) {
        if (isLoadMore) {
            this.notifies.remove(this.notifies.size() - 1);
            this.adapter.notifyItemRemoved(this.notifies.size() - 1);
            this.notifies.addAll(notifies);
            this.adapter.notifyDataSetChanged();
        } else {
            this.notifies.clear();
            this.notifies.addAll(notifies);
            loadList();
        }

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }

        this.isLoading = this.notifies.size() < totalRecord;
    }

    @Override
    public void onGetNotificationError() {
        if (mTab == 0) {
            notifies = presenter.getNotifiesOffline();
        } else {
            notifies = presenter.getUnReadNotifiesOffline();
        }

        loadList();
    }

    @Override
    public void onItemClick(int pos) {
        String url;
        if (mTab == 1) {
            url = notifies.get(pos).getLink();
            presenter.markAsRead(notifies.get(pos).getResourceId());
            notifies.remove(pos);
            adapter.notifyItemRemoved(pos);
        } else {
            url = notifies.get(pos).getLink();
            if (!notifies.get(pos).isFlgRead()) {
                notifies.get(pos).setFlgRead(true);
                adapter.notifyItemChanged(pos);
                presenter.markAsRead(notifies.get(pos).getResourceId());
            }
        }

        ActivityController.share.goToDocumentDetail(this, url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Notification screen");
    }

    @Override
    protected void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }

    @Override
    public void onRefresh() {
        if (mTab == 0) {
            presenter.getNotifies(0, Constants.mDataLimit, false);
        } else {
            presenter.getUnReadNotifies(0, Constants.mDataLimit, false);
        }
    }
}