package com.vuthao.VNADCM.category;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuthao.VNADCM.account.AccountFragment;
import com.vuthao.VNADCM.base.AnimationController;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.activity.BaseFragment;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.category.adapter.CategoryAdapter;
import com.vuthao.VNADCM.category.presenter.CategoryPresenter;
import com.vuthao.VNADCM.databinding.FragmentCategoryBinding;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class CategoryFragment extends BaseFragment implements CategoryPresenter.CategoryListener, CategoryAdapter.CategoryListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private FragmentCategoryBinding binding;
    private CategoryPresenter presenter;
    private CategoryAdapter adapter;
    private ArrayList<DocumentAreaCategory> categories;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        init();
        loadCategories();

        binding.imgAvatar.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);

        return binding.getRoot();
    }

    private void init() {
        presenter = new CategoryPresenter(this);
        categories = new ArrayList<>();
        binding.lvItems.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);

        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
    }

    private void loadCategories() {
        categories = presenter.getParentCategories();
        if (categories.size() > 0) {
            adapter = new CategoryAdapter(getContext(), categories, this);
            binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapter));

            binding.shimmer.stopShimmerAnimation();
            binding.shimmer.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetCategoriesSuccess(ArrayList<DocumentAreaCategory> categories) {
        this.categories = categories;
        loadCategories();
    }

    @Override
    public void onRefreshSuccess() {
        loadCategories();
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(int pos) {
        DetailCategoryFragment detailCategoryFragment = new DetailCategoryFragment(categories.get(pos).getID());
        getParentFragmentManager().beginTransaction()
                .replace(binding.container.getId(), detailCategoryFragment, "DetailCategoryFragment")
                .addToBackStack("DetailCategoryFragment")
                .commit();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgAvatar) {
            AnimationController.share.fadeIn(binding.imgAvatar, getContext());
            AccountFragment bottomSheetAccountDialog = new AccountFragment();
            bottomSheetAccountDialog.show(getParentFragmentManager(), AccountFragment.TAG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Category screen");
    }

    @Override
    public void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        presenter.updateData(getContext());
    }
}