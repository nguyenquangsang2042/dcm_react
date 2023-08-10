package com.vuthao.VNADCM.activity;

import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.ActivityController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.model.app.Settings;
import com.vuthao.VNADCM.category.CategoryFragment;
import com.vuthao.VNADCM.databinding.ActivityTabBinding;
import com.vuthao.VNADCM.favorite.FavoriteFragment;
import com.vuthao.VNADCM.home.HomeFragment;
import com.vuthao.VNADCM.search.SearchFragment;

public class TabActivity extends BaseActivity implements View.OnClickListener, ApiAuthController.ApiVersionListener {
    private ActivityTabBinding binding;
    private ApiAuthController apiAuthController;
    public static TabActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTabBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        instance = this;

        apiAuthController = new ApiAuthController();

        selectHome();
        checkNotification();

        binding.tabActivityMainCategory.setOnClickListener(this);
        binding.tabActivityMainHome.setOnClickListener(this);
        binding.tabActivityMainSearch.setOnClickListener(this);
        binding.tabActivityMainFavorite.setOnClickListener(this);
    }

    private void checkNotification() {
        if (!Constants.mUrl.isEmpty()) {
            ActivityController.share.goToDocumentDetail(this, Constants.mUrl);
            Constants.mUrl = "";
        }
    }

    private void selectHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.frMain.getId(), new HomeFragment(), "HomeFragment")
                .commit();
    }

    private void selectCategory() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.frMain.getId(), new CategoryFragment(), "CategoryFragment")
                .commit();
    }

    private void selectSearch() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.frMain.getId(), new SearchFragment(), "SearchFragment")
                .commit();
    }

    private void selectFavorite() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.frMain.getId(), new FavoriteFragment(), "FavoriteFragment")
                .commit();
    }

    private void setColorTab(int tab) {
        switch (tab) {
            case 0: {
                binding.imgActivityMainHome.setColorFilter(ContextCompat.getColor(this, R.color.clYellow));
                binding.tvActivityMainHome.setTextColor(ContextCompat.getColor(this, R.color.clYellow));

                binding.imgActivityMainCategory.setColorFilter(null);
                binding.tvActivityMainCategory.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainSearch.setColorFilter(null);
                binding.tvActivityMainSearch.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainFavorite.setColorFilter(null);
                binding.tvActivityMainFavorite.setTextColor(ContextCompat.getColor(this, R.color.white));

                break;
            }
            case 1: {
                binding.imgActivityMainCategory.setColorFilter(ContextCompat.getColor(this, R.color.clYellow));
                binding.tvActivityMainCategory.setTextColor(ContextCompat.getColor(this, R.color.clYellow));

                binding.imgActivityMainHome.setColorFilter(ContextCompat.getColor(this, R.color.clWhiteTab));
                binding.tvActivityMainHome.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainSearch.setColorFilter(null);
                binding.tvActivityMainSearch.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainFavorite.setColorFilter(null);
                binding.tvActivityMainFavorite.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            }
            case 2: {
                binding.imgActivityMainSearch.setColorFilter(ContextCompat.getColor(this, R.color.clYellow));
                binding.tvActivityMainSearch.setTextColor(ContextCompat.getColor(this, R.color.clYellow));

                binding.imgActivityMainCategory.setColorFilter(null);
                binding.tvActivityMainCategory.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainHome.setColorFilter(ContextCompat.getColor(this, R.color.clWhiteTab));
                binding.tvActivityMainHome.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainFavorite.setColorFilter(null);
                binding.tvActivityMainFavorite.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            }
            case 3: {
                binding.imgActivityMainFavorite.setColorFilter(ContextCompat.getColor(this, R.color.clYellow));
                binding.tvActivityMainFavorite.setTextColor(ContextCompat.getColor(this, R.color.clYellow));

                binding.imgActivityMainCategory.setColorFilter(null);
                binding.tvActivityMainCategory.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainHome.setColorFilter(ContextCompat.getColor(this, R.color.clWhiteTab));
                binding.tvActivityMainHome.setTextColor(ContextCompat.getColor(this, R.color.white));

                binding.imgActivityMainSearch.setColorFilter(null);
                binding.tvActivityMainSearch.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tabActivityMainCategory) {
            selectCategory();
            setColorTab(1);
        } else if (v == binding.tabActivityMainSearch) {
            selectSearch();
            setColorTab(2);
        } else if (v == binding.tabActivityMainFavorite) {
            selectFavorite();
            setColorTab(3);
        } else if (v == binding.tabActivityMainHome) {
            selectHome();
            setColorTab(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiAuthController.checkCurrentVersion(this);
    }

    @Override
    public void onGetCurrentVersionSuccess(Settings settings) {
        String currentVersion = Utility.share.getCurrentVersion(this);
        String newVersion = settings.getVALUE();
        if (Utility.share.compareVersion(currentVersion, newVersion)) {
            String mess = getString(R.string.force_update);
            String ok = getString(R.string.ok);

            Utility.share.showForceUpdateDialog(mess, "", ok, null, this, () -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.STORE_URL)));
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }
}