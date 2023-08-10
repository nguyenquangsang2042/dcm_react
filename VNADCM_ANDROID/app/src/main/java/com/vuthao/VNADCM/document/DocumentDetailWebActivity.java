package com.vuthao.VNADCM.document;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.vuthao.VNADCM.BuildConfig;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.NetworkUtil;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.session.PersistentCookieStore;
import com.vuthao.VNADCM.base.event.EventChange;
import com.vuthao.VNADCM.base.event.EventDispatcher;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentType;
import com.vuthao.VNADCM.base.realm.RealmDocumentTypeController;
import com.vuthao.VNADCM.databinding.ActivityDocumentWebBinding;
import com.vuthao.VNADCM.document.presenter.DocmentWebPresenter;
import com.vuthao.VNADCM.login.LoginActivity;

import org.json.JSONObject;

import java.io.File;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentDetailWebActivity extends BaseActivity implements View.OnClickListener,
        DocmentWebPresenter.DocmentWebListener,
        DownloadListener,
        DocumentDownload.DocumentDownloadListener,
        CompoundButton.OnCheckedChangeListener,
        SwipeRefreshLayout.OnRefreshListener {
    private ActivityDocumentWebBinding binding;
    private DocmentWebPresenter presenter;
    private int mResouceId;
    private int mDocumentGroupId;
    private int mCategoryId;
    private String mToken;
    private String mUrl;
    private RealmDocumentTypeController realmDocumentTypeController;
    private DocumentType documentType;
    private ValueCallback<Uri[]> uploadComment;
    private static final String TAG = DocumentDetailWebActivity.class.getSimpleName();
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private DocumentWebClient docClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDocumentWebBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        configureWebView();
        loadData();

        binding.imgBack.setOnClickListener(this);
        binding.swipeRefresh.setOnRefreshListener(this);
        binding.switchOfflineMode.setOnCheckedChangeListener(this);
    }

    private void init() {
        presenter = new DocmentWebPresenter(this);
        realmDocumentTypeController = new RealmDocumentTypeController();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mResouceId = bundle.getInt("ResourceId", 0);
            mDocumentGroupId = bundle.getInt("DocumentGroupId", 1);
            mCategoryId = bundle.getInt("CategoryId", 0);
            mUrl = bundle.getString("Url", "");
            documentType = realmDocumentTypeController.getItem(mCategoryId);

            if (documentType != null) {
                String df = getString(R.string.document_type) + " / ";
                df += CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066 ? documentType.getTitle() : documentType.getTitleEN();
                binding.tvTitle.setText(df);
            } else {
                binding.lnTitle.setVisibility(View.GONE);
            }
        } else {
            binding.lnTitle.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        presenter.getDocumentById(mResouceId);
        presenter.getTokenWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        String userAgent = String.format("%s [%s/%s]", webSettings.getUserAgentString(), "App Android", BuildConfig.VERSION_NAME);
        webSettings.setUserAgentString(userAgent);
        docClient = new DocumentWebClient();
        binding.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.setHorizontalScrollBarEnabled(true);
        binding.webView.setVerticalScrollBarEnabled(true);
        binding.webView.setWebViewClient(docClient);
        binding.webView.setWebChromeClient(new DocumentWebChromeClient());
        binding.webView.setDownloadListener(this);
    }

    private void loadUrl() {
        File file = new File(getFilesDir() +"/53c8cd07-80ef-4f6c-af57-95774373a85b/53c8cd07-80ef-4f6c-af57-95774373a85b.html");
        if(file.exists())
        {
            binding.webView.loadUrl(file.getPath());
        }
        else if (!mUrl.isEmpty()) {
            binding.webView.loadUrl(mUrl + "&autoid=" + mToken);
        } else {
            String url = Constants.BASE_URL + "/" + Constants.SUB_SITE
                    + "/frontend/pages/VNADetailVB.aspx?rid=" + mResouceId
                    + "&gid=" + mDocumentGroupId
                    + "&cid=" + mCategoryId
                    + "&autoid=" + mToken
                    + "&Mobile=thachdepzai"
                    + "&lang=" + (getPreferencesController().getLocaleId().equals("1066") ? "vi" : "en");

            binding.webView.loadUrl(url);
            Log.i("Url Web Document", url);
        }
    }

    private void goBack() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            goBack();
        }
    }

    @Override
    public void onGetTokenWebSuccess(String token) {
        mToken = token;
        loadUrl();
    }

    @Override
    public void onGetTokenWebError() {
        //binding.tvNodata.setVisibility(View.VISIBLE);
        loadUrl();
    }

    @Override
    public void onGetDocumentSuccess() {
        EventDispatcher.getInstance().post(EventChange.DOCUMENT_RECENTLY);
    }

    @Override
    public void onGetDocumentError() {
        Log.d("ERROR", "onGetDocumentError");
    }

    @Override
    public void onAuthCMSSuccess() {
    }

    @Override
    public void onAuthCMSError() {
        onGetTokenWebError();
    }

    @Override
    public void onDownloadStart(String url, String userAgent,
                                String contentDisposition, String mimetype,
                                long contentLength) {
        if (contentLength == 0) {
            onDownloadError();
        } else {
            String fileName = contentDisposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
            String folderName = presenter.getFolderName(mResouceId);
            new DocumentDownload(this, this, contentLength)
                    .execute(url, folderName, fileName);
        }
    }

    @Override
    public void onDownloadSuccess() {
        hideProgressDialog();
        showToast(getString(R.string.download_completed), null);
    }

    @Override
    public void onDownloadError() {
        hideProgressDialog();
        showToast(getString(R.string.dowload_error), null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DCMApplication.getInstance().trackScreenView("Detail document view screen");
    }

    ActivityResultLauncher<Intent> commentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (uploadComment == null) return;
                        uploadComment.onReceiveValue(WebChromeClient
                                .FileChooserParams
                                .parseResult(result
                                        .getResultCode(), result.getData()));
                        uploadComment = null;
                    }
                }
            });

    @Override
    public void onRefresh() {
        binding.webView.reload();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "onCheckedChanged: " + docClient.DocumentGroupId);
            Log.d(TAG, "onCheckedChanged: " + docClient.DocumentTypeId);
            Log.d(TAG, "onCheckedChanged: " + docClient.DocumentId);
            Log.d(TAG, "onCheckedChanged: " + docClient.IsArchive);
            Log.d(TAG, "onCheckedChanged: " + docClient.Version);
            Log.d(TAG, "onCheckedChanged: " + docClient.IsDivSection);
            Map<String,Object>data=new HashMap<>();
            data.put("DocumentId",Functions.isNullOrEmpty(((String)docClient.DocumentId).replaceAll("^\"|\"$", ""))?"":Integer.parseInt(((String)docClient.DocumentId).replaceAll("^\"|\"$", "")));
            data.put("DocumentTypeId",Functions.isNullOrEmpty(((String)docClient.DocumentTypeId).replaceAll("^\"|\"$", ""))?"":Integer.parseInt(((String)docClient.DocumentTypeId).replaceAll("^\"|\"$", "")));
            data.put("DocumentGroupId",Functions.isNullOrEmpty(((String)docClient.DocumentGroupId).replaceAll("^\"|\"$", ""))?"":Integer.parseInt(((String)docClient.DocumentGroupId).replaceAll("^\"|\"$", "")));
            data.put("IsArchive",Functions.isNullOrEmpty(((String)docClient.IsArchive).replaceAll("^\"|\"$", ""))?"":Integer.parseInt(((String)docClient.IsArchive).replaceAll("^\"|\"$", "")));
            data.put("Version",Functions.isNullOrEmpty(((String)docClient.Version).replaceAll("^\"|\"$", ""))?"":Integer.parseInt(((String)docClient.Version).replaceAll("^\"|\"$", "")));
            String json = new Gson().toJson(data);
            presenter.getHTML(json,1066,getApplicationContext());
            Utility.share.showAlertWithOnlyOK(getString(R.string.offline_enable), this, () -> {
            });
        } else {
            Utility.share.showAlertWithOnlyOK(getString(R.string.offline_disable), this, () -> {
            });
        }
    }

    public class DocumentWebClient extends WebViewClient {
        public Object IsDivSection;
        public Object DocumentId;
        public Object DocumentTypeId;
        public Object DocumentGroupId;
        public Object IsArchive;
        public Object Version;

        @Override
        public boolean shouldOverrideUrlLoading(WebView web, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.contains("download&tbl=documentattachfiles")) {
                showProgressDialog();
                web.loadUrl(request.getUrl().toString());
            } else {
                if (url.contains("not-found") || url.contains("access-denied")) {
                    web.loadUrl(url + "&Mobile=thachdepzai");
                } else {
                    List<Integer> pars = Functions.share.getParameterUrlDoc(url);
                    if (pars.size() > 0) {
                        String u = Constants.BASE_URL + "/" + Constants.SUB_SITE
                                + "/frontend/pages/VNADetailVB.aspx?rid=" + pars.get(0)
                                + "&gid=" + pars.get(1)
                                + "&cid=" + pars.get(2)
                                + "&autoid=" + mToken
                                + "&Mobile=thachdepzai"
                                + "&lang=" + (getPreferencesController().getLocaleId().equals("1066") ? "vi" : "en");
                        web.loadUrl(u);
                    }
                }
            }

            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            hideProgressDialog();
            if (NetworkUtil.getConnectivityStatus(DocumentDetailWebActivity.this) == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                showToast(getString(R.string.no_connection), null);
            } else {
                String url = view.getUrl();
                if (url.contains("download&tbl=documentattachfiles")) {
                    showToast(getString(R.string.dowload_error), null);
                } else {
                    showToast(getString(R.string.something_wrong), null);
                }
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            binding.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.evaluateJavascript("_VBINFO.IsDivSection", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    IsDivSection = value;

                }
            });
            view.evaluateJavascript("_VBINFO.DocumentId", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    DocumentId = value;
                }
            });
            view.evaluateJavascript("_VBINFO.DocumentTypeId", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    DocumentTypeId = value;
                }
            });
            view.evaluateJavascript("_DocumentGroupId", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    DocumentGroupId = value;
                }
            });
            view.evaluateJavascript("_QUERYINFO.IsArchive", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    IsArchive = value;
                }
            });
            view.evaluateJavascript("_VBINFO.Version", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Version = value;
                }
            });
        }
    }

    public class DocumentWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            binding.progressBar.setProgress(newProgress);
            binding.progressBar.setVisibility(newProgress == 100 ? View.INVISIBLE : View.VISIBLE);
            if (binding.swipeRefresh.isRefreshing()) {
                binding.swipeRefresh.setRefreshing(false);
            }
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadComment != null) {
                uploadComment.onReceiveValue(null);
                uploadComment = null;
            }

            uploadComment = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            try {
                commentResultLauncher.launch(intent);
            } catch (ActivityNotFoundException e) {
                uploadComment = null;
                showToast(getString(R.string.error_file_chooser), null);
                Log.d(TAG, e.getMessage());
                return false;
            }
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.swipeRefresh.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                () -> {
                    binding.swipeRefresh.setEnabled(binding.webView.getScrollY() == 0);
                });
    }

    @Override
    protected void onStop() {
        binding.swipeRefresh.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }
}