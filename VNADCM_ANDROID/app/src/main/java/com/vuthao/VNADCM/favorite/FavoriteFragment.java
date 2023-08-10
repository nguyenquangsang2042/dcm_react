package com.vuthao.VNADCM.favorite;

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
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.base.model.custom.MasterData;
import com.vuthao.VNADCM.base.realm.RealmFavoriteController;
import com.vuthao.VNADCM.databinding.FragmentFavoriteBinding;
import com.vuthao.VNADCM.favorite.adapter.FavoriteAdapter;
import com.vuthao.VNADCM.favorite.presenter.FavoritePresenter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class FavoriteFragment extends BaseFragment implements View.OnClickListener, FavoritePresenter.FavoriteListener, FavoriteAdapter.FavoriteListener, SwipeRefreshLayout.OnRefreshListener {
    private FragmentFavoriteBinding binding;
    private ArrayList<FavoriteFolder> favorites;
    private FavoritePresenter presenter;
    private FavoriteAdapter adapter;
    private RealmFavoriteController realmFavoriteController;

    public FavoriteFragment() {
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
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        init();
        loadList();

        binding.imgAvatar.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);

        return binding.getRoot();
    }

    private void init() {
        realmFavoriteController = new RealmFavoriteController();
        presenter = new FavoritePresenter(this);
        favorites = new ArrayList<>();

        binding.lvItems.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);

        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
    }

    private void loadList() {
        favorites = realmFavoriteController.getParentItems();

        if (favorites.size() > 0) {
            adapter = new FavoriteAdapter(getContext(), favorites, this);
            binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapter));

            binding.shimmer.stopShimmerAnimation();
            binding.shimmer.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
        }
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
        DCMApplication.getInstance().trackScreenView("Favorite main screen");
    }

    @Override
    public void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onItemClick(int pos) {
        DetailFavoriteFragment favoriteFragment = new DetailFavoriteFragment(favorites.get(pos).getID());
        getParentFragmentManager().beginTransaction()
                .replace(binding.container.getId(), favoriteFragment, "DetailFavoriteFragment")
                .addToBackStack("DetailFavoriteFragment")
                .commit();
    }

    @Override
    public void onRefresh() {
        presenter.updateData(getContext());
    }

    @Override
    public void onRefreshSuccess() {
        loadList();
        binding.swipeRefresh.setRefreshing(false);
    }
}