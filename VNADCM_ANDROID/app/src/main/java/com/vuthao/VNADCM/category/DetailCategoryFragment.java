package com.vuthao.VNADCM.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.realm.RealmCategoryController;
import com.vuthao.VNADCM.category.adapter.DetailCategoryAdapter;
import com.vuthao.VNADCM.category.adapter.DetailCategoryThumbAdapter;
import com.vuthao.VNADCM.category.adapter.TreeCategoryAdapter;
import com.vuthao.VNADCM.category.presenter.CategoryPresenter;
import com.vuthao.VNADCM.databinding.FragmentDetailCategoryBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class DetailCategoryFragment extends BaseFragment implements View.OnClickListener, TreeCategoryAdapter.TreeCategoryListener, CategoryPresenter.DocumentCategoryListener, DetailCategoryThumbAdapter.DetailCategoryThumbListener, DetailCategoryAdapter.DetailCategoryListener, SwipeRefreshLayout.OnRefreshListener {
    private FragmentDetailCategoryBinding binding;
    private int categoryId;
    private RealmCategoryController realmCategoryController;
    private ArrayList<DocumentAreaCategory> categoriesChild;
    private ArrayList<DocumentCategory> documentCategories;
    private TreeCategoryAdapter treeCategoryAdapter;
    private DocumentAreaCategory category;
    private CategoryPresenter presenter;
    private DetailCategoryThumbAdapter detailCategoryThumbAdapter;
    private DetailCategoryAdapter detailCategoryAdapter;
    private LinearLayoutManager mLayoutManagerThumb;
    private LinearLayoutManager mLayoutManager;
    private boolean isThumb = true;
    private boolean isLoadMore;
    private ArrayList<Integer> categorisId;

    public DetailCategoryFragment() {
        // Required empty public constructor
    }

    public DetailCategoryFragment(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailCategoryBinding.inflate(inflater, container, false);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        init();
        loadCategories();
        addLoadMore();

        presenter.getListDocumentCategory(categoryId, Constants.mDataLimit - 10, 0, false, this);

        binding.drawerLayout.post(() -> {
            DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) binding.lnActivityMainLeftMenu.getLayoutParams();
            params.width = (Functions.share.getScreenWidth() * 2) / 3;
            binding.lnActivityMainLeftMenu.setLayoutParams(params);
        });

        binding.imgBack.setOnClickListener(this);
        binding.imgMenu.setOnClickListener(this);
        binding.imgList.setOnClickListener(this);
        binding.lnActivityMainLeftMenu.setOnClickListener(this);
        binding.getRoot().setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);

        return binding.getRoot();
    }

    private void init() {
        presenter = new CategoryPresenter();
        realmCategoryController = new RealmCategoryController();
        mLayoutManagerThumb = new GridLayoutManager(getContext(), 2);
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        categoriesChild = new ArrayList<>();
        categorisId = new ArrayList<>();
        documentCategories = new ArrayList<>();
        category = realmCategoryController.getItem(categoryId);
        categorisId.add(categoryId);

        if (category != null) {
            binding.tvTitle.setText(sBaseActivity.getPreferencesController().getLocaleId().equals("1066") ? category.getTitle() : category.getTitleEN());
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
                    if (mLayoutManagerThumb.findLastVisibleItemPosition() == documentCategories.size() - 1) {
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
                    if (mLayoutManager.findLastVisibleItemPosition() == documentCategories.size() - 1) {
                        loadMore();
                        isLoadMore = false;
                    }
                }
            }
        });
    }

    private void loadMore() {
        this.documentCategories.add(null);
        if (isThumb) {
            this.detailCategoryThumbAdapter.notifyItemInserted(this.documentCategories.size() - 1);
        } else {
            this.detailCategoryAdapter.notifyItemInserted(this.documentCategories.size() - 1);
        }

        presenter.getListDocumentCategory(categoryId, Constants.mDataLimit - 10, this.documentCategories.size() - 1, true, this);
    }

    private void loadCategories() {
        categoriesChild = realmCategoryController.getChildByParentId(categoryId);
        if (categoriesChild.size() > 0) {
            treeCategoryAdapter = new TreeCategoryAdapter(getContext(), categoriesChild, this);
            binding.navigationActivityMainLeftMenu.setAdapter(treeCategoryAdapter);

            binding.lnNodataChild.setVisibility(View.GONE);
            binding.navigationActivityMainLeftMenu.setVisibility(View.VISIBLE);
        } else {
            binding.lnNodataChild.setVisibility(View.VISIBLE);
            binding.navigationActivityMainLeftMenu.setVisibility(View.GONE);
        }
    }

    private void loadList() {
        if (documentCategories.size() > 0) {
            if (isThumb) {
                if (detailCategoryThumbAdapter == null) {
                    detailCategoryThumbAdapter = new DetailCategoryThumbAdapter(getContext(), documentCategories, this);
                    binding.lvItemsThumb.setAdapter(new AlphaInAnimationAdapter(detailCategoryThumbAdapter));
                } else {
                    detailCategoryThumbAdapter.notifyDataSetChanged();
                    binding.lvItemsThumb.scrollToPosition(0);
                }

                binding.shimmer.stopShimmerAnimation();
                binding.shimmer.setVisibility(View.GONE);
                binding.lvItemsThumb.setVisibility(View.VISIBLE);
                binding.lvItems.setVisibility(View.GONE);
                binding.lnNodata.setVisibility(View.GONE);
            } else {
                if (detailCategoryAdapter == null) {
                    detailCategoryAdapter = new DetailCategoryAdapter(getContext(), documentCategories, this);
                    binding.lvItems.setAdapter(new AlphaInAnimationAdapter(detailCategoryAdapter));
                } else {
                    detailCategoryAdapter.notifyDataSetChanged();
                    binding.lvItems.scrollToPosition(0);
                }

                binding.shimmer.stopShimmerAnimation();
                binding.shimmer.setVisibility(View.GONE);
                binding.lvItemsThumb.setVisibility(View.GONE);
                binding.lvItems.setVisibility(View.VISIBLE);
                binding.lnNodata.setVisibility(View.GONE);
            }
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.lvItemsThumb.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.GONE);
            binding.lnNodata.setVisibility(View.VISIBLE);
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
        if (categorisId.size() > 1) {
            categorisId.remove(categorisId.size() - 1);
            category = realmCategoryController.getItem(categorisId.get(categorisId.size() - 1));
            categoryId = categorisId.get(categorisId.size() - 1);
            binding.tvTitle.setText(binding.tvTitle.getText().toString().substring(0, binding.tvTitle.getText().toString().lastIndexOf('/')));
            loadCategories();
            presenter.getListDocumentCategory(categoryId, Constants.mDataLimit - 10, 0, false, this);
        } else {
            getParentFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            back();
        } else if (v == binding.imgMenu) {
            if (binding.drawerLayout.isOpen()) {
                binding.drawerLayout.closeDrawer(binding.lnActivityMainLeftMenu);
            } else {
                binding.drawerLayout.openDrawer(binding.lnActivityMainLeftMenu);
            }
        } else if (v == binding.imgList) {
            enableMode();
        }
    }

    @Override
    public void onItemClick(int pos) {
        binding.shimmer.startShimmerAnimation();
        binding.shimmer.setVisibility(View.VISIBLE);

        categoryId = categoriesChild.get(pos).getID();
        categorisId.add(categoryId);
        category = realmCategoryController.getItem(categoryId);
        binding.tvTitle.setText(binding.tvTitle.getText().toString() + " / " + category.getTitle());
        loadCategories();
        presenter.getListDocumentCategory(categoryId, Constants.mDataLimit - 10, 0, false, this);
        binding.drawerLayout.closeDrawer(binding.lnActivityMainLeftMenu);
    }

    @Override
    public void onGetDocumentCategoriesSuccess(ArrayList<DocumentCategory> categories, boolean isLoadMore, int totalRecord) {
        if (isLoadMore) {
            this.documentCategories.remove(this.documentCategories.size() - 1);
            if (isThumb) {
                this.detailCategoryThumbAdapter.notifyItemRemoved(this.documentCategories.size() - 1);
            } else {
                this.detailCategoryAdapter.notifyItemRemoved(this.documentCategories.size() - 1);
            }

            this.documentCategories.addAll(categories);

            if (isThumb) {
                this.detailCategoryThumbAdapter.notifyDataSetChanged();
            } else {
                this.detailCategoryAdapter.notifyDataSetChanged();
            }
        } else {
            this.documentCategories.clear();
            this.documentCategories.addAll(categories);
            loadList();
        }

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }
        this.isLoadMore = this.documentCategories.size() < totalRecord;
    }

    @Override
    public void onGetDocumentCategoriesError() {
        documentCategories = presenter.getDocmentCategoryOffline(categoryId);
        loadList();
    }

    @Override
    public void onItemThumbClick(int pos) {
        ActivityController.share.goToDocumentDetail(getActivity(), documentCategories.get(pos).getStorageCode());
    }

    @Override
    public void onItemDetailCategoryClick(int pos) {
        ActivityController.share.goToDocumentDetail(getActivity(), documentCategories.get(pos).getStorageCode());
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Detail document category screen");
    }

    @Override
    public void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        presenter.getListDocumentCategory(categoryId, Constants.mDataLimit - 10, 0, false, this);
    }
}