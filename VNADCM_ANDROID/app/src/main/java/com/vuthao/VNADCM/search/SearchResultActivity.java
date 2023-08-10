package com.vuthao.VNADCM.search;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.vuthao.VNADCM.BuildConfig;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.keyboard.KeyboardManager;
import com.vuthao.VNADCM.base.model.app.SearchHistory;
import com.vuthao.VNADCM.databinding.ActivitySearchResultBinding;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.search.adapter.SearchHistoryAdapter;
import com.vuthao.VNADCM.search.presenter.SearchPresenter;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity implements SearchHistoryAdapter.SearchHistoryListener, View.OnClickListener, TextWatcher, SearchPresenter.TokenWebViewListener, TextView.OnEditorActionListener {
    private ActivitySearchResultBinding binding;
    private SearchHistoryAdapter adapter;
    private ArrayList<SearchHistory> searchHistories;
    private SearchPresenter presenter;
    private String keyword;
    private String tokenWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        loadHistories();
        loadKeyword();

        binding.tvClose.setOnClickListener(this);
        binding.edtContent.setOnClickListener(this);
        binding.imgDeleteSearch.setOnClickListener(this);
        binding.edtContent.setOnEditorActionListener(this);
        binding.edtContent.addTextChangedListener(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        presenter = new SearchPresenter();
        searchHistories = new ArrayList<>();

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        String userAgent = String.format("%s [%s/%s]", webSettings.getUserAgentString(), "App Android", BuildConfig.VERSION_NAME);
        webSettings.setUserAgentString(userAgent);

        binding.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.setHorizontalScrollBarEnabled(true);
        binding.webView.setVerticalScrollBarEnabled(true);
        binding.webView.setWebViewClient(new SearchViewWebClient());
        binding.webView.setWebChromeClient(new SearchWebChromeClient());
        binding.webView.setBackgroundColor(Color.WHITE);
        binding.webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        binding.lvItems.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);

        binding.webView.setVisibility(View.GONE);
    }

    private void loadKeyword() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            keyword = bundle.getString("Keyword", "");
            if (!keyword.isEmpty()) {
                binding.edtContent.setText(keyword);
                binding.edtContent.requestFocus();
                binding.edtContent.setSelection(binding.edtContent.length());
                binding.imgDeleteSearch.setVisibility(View.VISIBLE);
                presenter.getTokenWebView(this);
            } else {
                binding.edtContent.requestFocus();
                binding.lvItems.setVisibility(View.VISIBLE);
            }
        } else {
            binding.edtContent.requestFocus();
            binding.lvItems.setVisibility(View.VISIBLE);
        }
    }

    private void loadHistories() {
        if (binding.lvItems.getVisibility() == View.VISIBLE) {
            ArrayList<SearchHistory> items = presenter.getSearchHistories();
            searchHistories.clear();
            searchHistories.addAll(items);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new SearchHistoryAdapter(this, searchHistories, this);
                binding.lvItems.setAdapter(adapter);
            }
        }
    }

    private void loadUrl() {
        String url = Constants.BASE_URL
                + "/"
                + Constants.SUB_SITE
                + "/frontend/pages/VNASearchDocument.aspx?kwd="
                + keyword + "&searchunittype=0&gid=1"
                + "&autoid=" + tokenWeb
                + "&lang=" + (getPreferencesController().getLocaleId().equals("1066") ? "vi" : "en");
        binding.webView.loadUrl(url);
    }

    @Override
    public void onItemHistoryClick(int pos) {
        binding.edtContent.setText(searchHistories.get(pos).getTitle());
    }

    @Override
    public void onItemHistoryDeleteClick(int pos) {
        searchHistories.remove(pos);
        adapter.notifyItemRemoved(pos);
        presenter.removeKeyword(searchHistories.get(pos).getTitle());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvClose) {
            KeyboardManager.hideKeyboard(binding.edtContent, this);
            onBackPressed();
        } else if (v == binding.edtContent) {
            selectEditText(binding.edtContent);
        } else if (v == binding.imgDeleteSearch) {
            binding.edtContent.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        this.keyword = editable.toString();

        binding.imgDeleteSearch.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
        binding.lvItems.setVisibility(editable.length() > 0 ? View.GONE : View.VISIBLE);
        loadHistories();
    }

    @Override
    public void onGetTokenSuccess(String token) {
        tokenWeb = token;
        loadUrl();
    }

    @Override
    public void onGetTokenError() {
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        if (i == EditorInfo.IME_ACTION_DONE) {
            if (keyword.length() > 2) {
                KeyboardManager.hideKeyboard(this);
                presenter.addKeyword(keyword);
                presenter.getTokenWebView(this);
                handled = true;
            } else {
                showToast(getString(R.string.require_search), null);
            }
        }

        return handled;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DCMApplication.getInstance().trackScreenView("Search result screen");
    }

    public class SearchViewWebClient extends WebViewClient {

        public SearchViewWebClient() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            ActivityController.share.goToDocumentDetail(SearchResultActivity.this, request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    public class SearchWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            binding.progressBar.setProgress(newProgress);
            binding.progressBar.setVisibility(newProgress == 100 ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }
}