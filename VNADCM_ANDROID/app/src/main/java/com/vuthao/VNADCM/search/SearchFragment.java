package com.vuthao.VNADCM.search;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.account.AccountFragment;
import com.vuthao.VNADCM.base.AnimationController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.activity.BaseFragment;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;
import com.vuthao.VNADCM.base.keyboard.KeyboardManager;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.SearchTrend;
import com.vuthao.VNADCM.databinding.FragmentSearchBinding;
import com.vuthao.VNADCM.search.adapter.SearchTrendAdapter;
import com.vuthao.VNADCM.search.presenter.SearchPresenter;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;

public class SearchFragment extends BaseFragment implements View.OnClickListener, SearchPresenter.SearchListener, SearchTrendAdapter.SearchTrendListener, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener {
    private FragmentSearchBinding binding;
    private SearchPresenter presenter;
    private ArrayList<SearchTrend> searchTrends;

    public SearchFragment() {
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        init();
        loadTrend();

        binding.imgAvatar.setOnClickListener(this);
        binding.edtContent.setOnClickListener(this);
        binding.edtContent.setOnEditorActionListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);
        return binding.getRoot();
    }

    private void init() {
        presenter = new SearchPresenter(this);
        searchTrends = new ArrayList<>();

        binding.lvItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);

        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
    }

    private void loadTrend() {
        searchTrends = presenter.getSearchTrend();
        if (searchTrends.size() > 0) {
            SearchTrendAdapter searchTrendAdapter = new SearchTrendAdapter(getContext(), searchTrends, this);
            binding.lvItems.setAdapter(searchTrendAdapter);

            binding.lnContent.setVisibility(View.VISIBLE);
        } else {
            binding.lnContent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgAvatar) {
            AnimationController.share.fadeIn(binding.imgAvatar, getContext());
            AccountFragment bottomSheetAccountDialog = new AccountFragment();
            bottomSheetAccountDialog.show(getParentFragmentManager(), AccountFragment.TAG);
        } else if (v == binding.edtContent) {
            sBaseActivity.selectEditText(binding.edtContent);
        }
    }


    @Override
    public void onSearchTrendClick(int pos) {
        presenter.addKeyword(searchTrends.get(pos).getTitle());
        Intent i = new Intent(getActivity(), SearchResultActivity.class);
        i.putExtra("Keyword", searchTrends.get(pos).getTitle());
        getActivity().startActivity(i);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        if (i == EditorInfo.IME_ACTION_DONE) {
            String keyword = binding.edtContent.getText().toString();
            if (keyword.length() > 2) {
                presenter.addKeyword(keyword);
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("Keyword", keyword);
                getActivity().startActivity(intent);
                handled = true;
            } else {
                sBaseActivity.showToast(getString(R.string.require_search),60, null);
            }
        }
        return handled;
    }

    @Override
    public void onPause() {
        KeyboardManager.hideKeyboard(binding.edtContent, getActivity());
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        DCMApplication.getInstance().trackScreenView("Search main screen");
    }

    @Override
    public void onRefresh() {
        presenter.refreshData(getContext());
    }

    @Override
    public void onRefreshSuccess() {
        loadTrend();
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefreshError() {

    }
}