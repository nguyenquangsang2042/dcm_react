package com.vuthao.VNADCM.favorite;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.activity.BaseFragment;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;
import com.vuthao.VNADCM.databinding.FragmentDetailFavoriteBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.favorite.adapter.DetailFavoriteAdapter;
import com.vuthao.VNADCM.favorite.adapter.DetailFavoriteThumbAdapter;
import com.vuthao.VNADCM.favorite.adapter.TreeFavoriteAdapter;
import com.vuthao.VNADCM.favorite.presenter.FavoritePresenter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class DetailFavoriteFragment extends BaseFragment implements View.OnClickListener, TreeFavoriteAdapter.TreeFavoriteListener, FavoritePresenter.DocumentFavoriteListener, DetailFavoriteAdapter.DetailFavoriteListener, DetailFavoriteThumbAdapter.DetailFavoriteThumbListener, SwipeRefreshLayout.OnRefreshListener {
    private FragmentDetailFavoriteBinding binding;
    private RealmFavoriteController realmFavoriteController;
    private ArrayList<FavoriteFolder> favoriteFolders;
    private ArrayList<DocumentFavorite> documentFavorites;
    private FavoriteFolder favoriteFolder;
    private TreeFavoriteAdapter treeFavoriteAdapter;
    private String favoriteId;
    private LinearLayoutManager mLayoutManagerThumb;
    private LinearLayoutManager mLayoutManager;
    private DetailFavoriteAdapter detailFavoriteAdapter;
    private DetailFavoriteThumbAdapter detailFavoriteThumbAdapter;
    private boolean isThumb = true;
    private boolean isLoadMore;
    private ArrayList<String> favoritesId;
    private FavoritePresenter presenter;

    public DetailFavoriteFragment() {
        // Required empty public constructor
    }

    public DetailFavoriteFragment(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailFavoriteBinding.inflate(inflater, container, false);

        init();
        loadTree();
        addLoadMore();

        presenter.getDocumentFavorite(favoriteId, Constants.mDataLimit - 10, 0, false, this);

        binding.drawerLayout.post(() -> {
            DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) binding.lnActivityMainLeftMenu.getLayoutParams();
            params.width = (Functions.share.getScreenWidth() * 2) / 3;
            binding.lnActivityMainLeftMenu.setLayoutParams(params);
        });

        binding.imgBack.setOnClickListener(this);
        binding.imgMenu.setOnClickListener(this);
        binding.imgList.setOnClickListener(this);
        binding.lnActivityMainLeftMenu.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);
        binding.getRoot().setOnClickListener(this);
        return binding.getRoot();
    }

    private void init() {
        presenter = new FavoritePresenter();
        realmFavoriteController = new RealmFavoriteController();
        favoriteFolders = new ArrayList<>();
        favoritesId = new ArrayList<>();
        documentFavorites = new ArrayList<>();
        favoriteFolder = realmFavoriteController.getItem(favoriteId);
        mLayoutManagerThumb = new GridLayoutManager(getContext(), 2);
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        favoritesId.add(favoriteId);

        if (favoriteFolder != null) {
            binding.tvTitle.setText(favoriteFolder.getTitle());
        } else {
            binding.tvTitle.setText("");
        }

        binding.navigationActivityMainLeftMenu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.navigationActivityMainLeftMenu.setHasFixedSize(true);
        binding.navigationActivityMainLeftMenu.setItemViewCacheSize(20);

        binding.lvItemsThumb.setHasFixedSize(true);
        binding.lvItemsThumb.setItemViewCacheSize(20);
        binding.lvItemsThumb.setLayoutManager(mLayoutManagerThumb);

        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);
        binding.lvItems.setLayoutManager(mLayoutManager);
    }

    private void addLoadMore() {
        binding.lvItemsThumb.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoadMore) {
                    if (mLayoutManagerThumb.findLastVisibleItemPosition() == documentFavorites.size() - 1) {
                        loadMore();
                        isLoadMore = false;
                    }
                }
            }
        });

        binding.lvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoadMore) {
                    if (mLayoutManager.findLastVisibleItemPosition() == documentFavorites.size() - 1) {
                        loadMore();
                        isLoadMore = false;
                    }
                }
            }
        });
    }


    private void loadMore() {
        this.documentFavorites.add(null);
        if (isThumb) {
            detailFavoriteThumbAdapter.notifyItemInserted(this.documentFavorites.size() - 1);
        } else {
            detailFavoriteAdapter.notifyItemInserted(this.documentFavorites.size() - 1);
        }

        presenter.getDocumentFavorite(favoriteId, Constants.mDataLimit - 10, this.documentFavorites.size() - 1, true, this);
    }

    private void loadTree() {
        favoriteFolders = realmFavoriteController.getItemByParentId(favoriteId);
        if (favoriteFolders.size() > 0) {
            treeFavoriteAdapter = new TreeFavoriteAdapter(getContext(), favoriteFolders, this);
            binding.navigationActivityMainLeftMenu.setAdapter(treeFavoriteAdapter);

            binding.navigationActivityMainLeftMenu.setVisibility(View.VISIBLE);
            binding.lnNodataChild.setVisibility(View.GONE);
        } else {
            binding.navigationActivityMainLeftMenu.setVisibility(View.GONE);
            binding.lnNodataChild.setVisibility(View.VISIBLE);
        }
    }

    private void loadList() {
        if (documentFavorites.size() > 0) {
            if (isThumb) {
                if (detailFavoriteThumbAdapter == null) {
                    detailFavoriteThumbAdapter = new DetailFavoriteThumbAdapter(getContext(), documentFavorites, this);
                    binding.lvItemsThumb.setAdapter(new AlphaInAnimationAdapter(detailFavoriteThumbAdapter));
                } else {
                    detailFavoriteThumbAdapter.notifyDataSetChanged();
                    binding.lvItemsThumb.scrollToPosition(0);
                }

                binding.shimmer.stopShimmerAnimation();
                binding.shimmer.setVisibility(View.GONE);
                binding.lnNodata.setVisibility(View.GONE);
                binding.lvItemsThumb.setVisibility(View.VISIBLE);
                binding.lvItems.setVisibility(View.GONE);
            } else {
                if (detailFavoriteAdapter == null) {
                    detailFavoriteAdapter = new DetailFavoriteAdapter(getContext(), documentFavorites, this);
                    binding.lvItems.setAdapter(new AlphaInAnimationAdapter(detailFavoriteAdapter));
                } else {
                    detailFavoriteAdapter.notifyDataSetChanged();
                    binding.lvItems.scrollToPosition(0);
                }

                binding.shimmer.stopShimmerAnimation();
                binding.shimmer.setVisibility(View.GONE);
                binding.lnNodata.setVisibility(View.GONE);
                binding.lvItemsThumb.setVisibility(View.GONE);
                binding.lvItems.setVisibility(View.VISIBLE);
            }
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.VISIBLE);
            binding.lvItemsThumb.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.GONE);
        }
    }

    private void enableMode() {
        isThumb = !isThumb;
        if (isThumb) {
            binding.imgList.setImageResource(R.drawable.icon_show_thumb);
        } else {
            binding.imgList.setImageResource(R.drawable.icon_show_list);
        }

        loadList();
    }

    private void back() {
        if (favoritesId.size() > 1) {
            favoritesId.remove(favoritesId.size() - 1);
            favoriteFolder = realmFavoriteController.getItem(favoritesId.get(favoritesId.size() - 1));
            favoriteId = favoritesId.get(favoritesId.size() - 1);
            binding.tvTitle.setText(binding.tvTitle.getText().toString().substring(0, binding.tvTitle.getText().toString().lastIndexOf('/')));
            loadTree();
            presenter.getDocumentFavorite(favoriteId, Constants.mDataLimit - 10, 0, false, this);
        } else {
            getParentFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgMenu) {
            if (binding.drawerLayout.isOpen()) {
                binding.drawerLayout.closeDrawer(binding.lnActivityMainLeftMenu);
            } else {
                binding.drawerLayout.openDrawer(binding.lnActivityMainLeftMenu);
            }
        } else if (v == binding.imgList) {
            enableMode();
        } else if (v == binding.imgBack) {
            back();
        }
    }

    @Override
    public void onTreeItemClick(int pos) {
        binding.shimmer.startShimmerAnimation();
        binding.shimmer.setVisibility(View.VISIBLE);

        favoriteId = favoriteFolders.get(pos).getID();
        favoritesId.add(favoriteId);
        favoriteFolder = realmFavoriteController.getItem(favoriteId);
        binding.tvTitle.setText(binding.tvTitle.getText().toString() + " / " + favoriteFolder.getTitle());
        loadTree();
        presenter.getDocumentFavorite(favoriteId, Constants.mDataLimit - 10, 0, false, this);
        binding.drawerLayout.closeDrawer(binding.lnActivityMainLeftMenu);
    }

    @Override
    public void onGetDocumentFavoriteSuccess(ArrayList<DocumentFavorite> favorites, boolean isLoadMore, int totalRecord) {
        if (isLoadMore) {
            this.documentFavorites.remove(this.documentFavorites.size() - 1);
            if (isThumb) {
                detailFavoriteThumbAdapter.notifyItemRemoved(this.documentFavorites.size() - 1);
            } else {
                detailFavoriteAdapter.notifyItemRemoved(this.documentFavorites.size() - 1);
            }

            this.documentFavorites.addAll(favorites);

            if (isThumb) {
                detailFavoriteThumbAdapter.notifyDataSetChanged();
            } else {
                detailFavoriteAdapter.notifyDataSetChanged();
            }
        } else {
            this.documentFavorites.clear();
            this.documentFavorites.addAll(favorites);
            loadList();
        }

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }
        this.isLoadMore = this.documentFavorites.size() < totalRecord;
    }

    @Override
    public void onGetDocumentFavoriteError() {
        documentFavorites = presenter.getDocumentFavoriteOffline(favoriteId);
        loadList();
    }

    @Override
    public void onDetailItemClick(int pos) {
        ActivityController.share.goToDocumentDetail(getActivity(), documentFavorites.get(pos).getResourceUrl());
    }

    @Override
    public void onDocumentThumbClick(int pos) {
        ActivityController.share.goToDocumentDetail(getActivity(), documentFavorites.get(pos).getResourceUrl());
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Favorite detail document screen");
    }

    @Override
    public void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        presenter.getDocumentFavorite(favoriteId, Constants.mDataLimit - 10, 0, false, this);
    }
}