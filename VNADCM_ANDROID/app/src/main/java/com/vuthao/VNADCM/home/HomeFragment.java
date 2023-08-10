package com.vuthao.VNADCM.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.account.AccountFragment;
import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.AnimationController;
import com.vuthao.VNADCM.base.Crypter;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.activity.BaseFragment;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;
import com.vuthao.VNADCM.base.event.EventChange;
import com.vuthao.VNADCM.base.event.EventDispatcher;
import com.vuthao.VNADCM.base.event.EventListener;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.custom.HomeResource;
import com.vuthao.VNADCM.databinding.FragmentHomeBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.home.adapter.DocumentAdapter;
import com.vuthao.VNADCM.home.presenter.HomePresenter;
import com.vuthao.VNADCM.notification.NotificationActivity;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class HomeFragment extends BaseFragment implements View.OnClickListener, HomePresenter.HomeListener, SwipeRefreshLayout.OnRefreshListener, EventListener {
    private FragmentHomeBinding binding;
    private ArrayList<Document> documents;
    private ArrayList<Document> recentlys;
    private ArrayList<Document> mostVieweds;
    private ArrayList<Document> favorites;
    private DocumentAdapter adapterNewest;
    private DocumentAdapter adapterRecently;
    private DocumentAdapter adapterMostView;
    private DocumentAdapter adapterFavorite;
    private HomePresenter presenter;
    private AnimationController animationController;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        init();
        loadData();

        binding.imgAvatar.setOnClickListener(this);
        binding.btnOpenNotification.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);

        return binding.getRoot();
    }

    private void init() {
        animationController = new AnimationController();
        presenter = new HomePresenter(this);
        documents = new ArrayList<>();
        recentlys = new ArrayList<>();
        mostVieweds = new ArrayList<>();
        favorites = new ArrayList<>();

        binding.lvNewest.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.lvNewest.setHasFixedSize(true);

        binding.lvRecently.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.lvRecently.setHasFixedSize(true);

        binding.lvFavorite.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.lvFavorite.setHasFixedSize(true);

        binding.lvMostView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.lvMostView.setHasFixedSize(true);

        binding.lvNewest.setNestedScrollingEnabled(false);
        binding.lvRecently.setNestedScrollingEnabled(false);
        binding.lvFavorite.setNestedScrollingEnabled(false);
        binding.lvMostView.setNestedScrollingEnabled(false);

        binding.btnOpenNotification
                .setBadgeTextFont(ResourcesCompat.getFont(getContext(), R.font.fonts));

        ImageLoader.getInstance().loadImageUser(getActivity(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
        EventDispatcher.getInstance().addListener(EventChange.DOCUMENT_RECENTLY, this);
    }

    private void loadData() {
        loadListRecently();
        presenter.getResourcesHome(5, 0);
    }

    private void loadNewestDocument() {
        if (documents.size() > 0) {
            adapterNewest = new DocumentAdapter(getContext(), documents, pos -> {
                openWeb(documents.get(pos).getUrl());
            });
            binding.lvNewest.setAdapter(new AlphaInAnimationAdapter(adapterNewest));

            binding.shimmerNewest.stopShimmerAnimation();
            binding.lnNodataNewest.setVisibility(View.GONE);
            binding.shimmerNewest.setVisibility(View.GONE);
            binding.lvNewest.setVisibility(View.VISIBLE);
        } else {
            binding.lnNodataNewest.setVisibility(View.VISIBLE);
            binding.lvNewest.setVisibility(View.GONE);
            binding.shimmerNewest.setVisibility(View.GONE);
        }
    }

    private void loadListRecently() {
        ArrayList<Document> docs = presenter.getDocumentRecently();
        if (docs.size() > 0) {
            recentlys.clear();
            recentlys.addAll(docs);

            if (adapterRecently == null) {
                adapterRecently = new DocumentAdapter(getContext(), recentlys, pos -> {
                    openWeb(recentlys.get(pos).getUrl());
                });

                binding.lvRecently.setAdapter(new AlphaInAnimationAdapter(adapterRecently));
            } else {
                adapterRecently.notifyDataSetChanged();
            }

            binding.shimmerRecently.stopShimmerAnimation();
            binding.lvRecently.setVisibility(View.VISIBLE);
            binding.lnNodataRecently.setVisibility(View.GONE);
            binding.shimmerRecently.setVisibility(View.GONE);
        } else {
            binding.lvRecently.setVisibility(View.GONE);
            binding.shimmerRecently.setVisibility(View.GONE);
            binding.lnNodataRecently.setVisibility(View.VISIBLE);
        }
    }

    private void loadMostView() {
        if (mostVieweds.size() > 0) {
            adapterMostView = new DocumentAdapter(getContext(), mostVieweds, pos -> {
                openWeb(mostVieweds.get(pos).getUrl());
            });
            binding.lvMostView.setAdapter(new AlphaInAnimationAdapter(adapterMostView));

            binding.shimmerMostView.stopShimmerAnimation();
            binding.lvMostView.setVisibility(View.VISIBLE);
            binding.lnNodataMostView.setVisibility(View.GONE);
            binding.shimmerMostView.setVisibility(View.GONE);
        } else {
            binding.lvMostView.setVisibility(View.GONE);
            binding.shimmerMostView.setVisibility(View.GONE);
            binding.lnNodataMostView.setVisibility(View.VISIBLE);
        }
    }

    private void loadFavorite() {
        if (favorites.size() > 0) {
            adapterFavorite = new DocumentAdapter(getContext(), favorites, pos -> {
                openWeb(favorites.get(pos).getUrl());
            });
            binding.lvFavorite.setAdapter(new AlphaInAnimationAdapter(adapterFavorite));

            binding.shimmerFavorite.stopShimmerAnimation();
            binding.lnNodataFavorite.setVisibility(View.GONE);
            binding.shimmerFavorite.setVisibility(View.GONE);
            binding.lvFavorite.setVisibility(View.VISIBLE);
        } else {
            binding.lnNodataFavorite.setVisibility(View.VISIBLE);
            binding.lvFavorite.setVisibility(View.GONE);
            binding.shimmerFavorite.setVisibility(View.GONE);
        }
    }

    private void openWeb(String url) {
        ActivityController.share.goToDocumentDetail(getActivity(), url);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgAvatar) {
            animationController.fadeIn(binding.imgAvatar, getContext());
            AccountFragment bottomSheetAccountDialog = new AccountFragment();
            bottomSheetAccountDialog.show(getParentFragmentManager(), AccountFragment.TAG);
        } else if (v == binding.btnOpenNotification) {
            animationController.fadeIn(binding.btnOpenNotification, getContext());
            Intent i = new Intent(getActivity(), NotificationActivity.class);
            getActivity().startActivity(i);
        }
    }

    @Override
    public void onRefresh() {
        presenter.updateData(getContext());
    }

    @Override
    public void onGetHomeResourceSuccess(HomeResource homeResource) {
        documents = homeResource.getViewDocumentNew();
        mostVieweds = homeResource.getDocumentMostView();
        favorites = homeResource.getDocumentFavorite();
        loadNewestDocument();
        loadMostView();
        loadFavorite();
    }

    @Override
    public void onRefreshSuccess() {
        presenter.getNotificationVisble();
        loadData();
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetHomeResourceError() {
        documents = presenter.getDocumentsNewest();
        mostVieweds = presenter.getDocumentsMostViewed();
        favorites = presenter.getDocumentsFavorite();
        loadNewestDocument();
        loadMostView();
        loadFavorite();
    }

    @Override
    public void onNotificationVisble(int count) {
        binding.btnOpenNotification.setBadgeValue(count);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getNotificationVisble();
        binding.shimmerNewest.startShimmerAnimation();
        binding.shimmerRecently.startShimmerAnimation();
        binding.shimmerMostView.startShimmerAnimation();
        binding.shimmerFavorite.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Home screen");
    }

    @Override
    public void onPause() {
        binding.shimmerNewest.stopShimmerAnimation();
        binding.shimmerRecently.stopShimmerAnimation();
        binding.shimmerMostView.stopShimmerAnimation();
        binding.shimmerFavorite.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        EventDispatcher.getInstance().removeListener(EventChange.DOCUMENT_RECENTLY, this);
        super.onDestroyView();
    }

    @Override
    public void onEvent(int id, Object... args) {
        if (id == EventChange.DOCUMENT_RECENTLY) {
            loadListRecently();
        }
    }
}