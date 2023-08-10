package com.vuthao.VNADCM.account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.vuthao.VNADCM.BuildConfig;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.account.presenter.AccountPresenter;
import com.vuthao.VNADCM.activity.TabActivity;
import com.vuthao.VNADCM.base.AnimationController;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.activity.BaseBottomSheetFragment;
import com.vuthao.VNADCM.base.event.EventChange;
import com.vuthao.VNADCM.base.event.EventDispatcher;
import com.vuthao.VNADCM.base.event.EventListener;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.comment.CommentActivity;
import com.vuthao.VNADCM.databinding.BottomSheetAccountBinding;
import com.vuthao.VNADCM.interactive.InteractiveActivity;
import com.vuthao.VNADCM.login.LoginActivity;
import com.vuthao.VNADCM.notification.config.ConfigNotificationActivity;
import com.vuthao.VNADCM.profile.ProfileFragment;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class AccountFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    public static final String TAG = "BottomSheetAccountDialog";
    private BottomSheetAccountBinding binding;
    private AccountPresenter presenter;
    private SharedPreferencesController sharedPreferencesController;
    private BottomSheetBehavior behavior;

    public AccountFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetAccountBinding.inflate(inflater, container, false);

        init();
        loadInfo();

        binding.tvClose.setOnClickListener(this);
        binding.lnLogout.setOnClickListener(this);
        binding.rlProfile.setOnClickListener(this);
        binding.lnComment.setOnClickListener(this);
        binding.rlInteractive.setOnClickListener(this);
        binding.rlConfig.setOnClickListener(this);
        binding.tvEN.setOnClickListener(this);
        binding.tvVN.setOnClickListener(this);

        return binding.getRoot();
    }

    private void init() {
        presenter = new AccountPresenter();
        sharedPreferencesController = new SharedPreferencesController(getActivity());

        String localeId = sharedPreferencesController.getLocaleId();

        if (localeId.equals("1066")) {
            binding.tvVN.setBackgroundResource(R.drawable.bg_border_language_selected);
            binding.tvVN.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            binding.tvVN.setEnabled(false);
        } else {
            binding.tvVN.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
            binding.tvVN.setTextColor(ContextCompat.getColor(getActivity(), R.color.clGray3));
            binding.tvVN.setPadding(0, 0, 0, 0);

            binding.tvEN.setBackgroundResource(R.drawable.bg_border_language_selected);
            binding.tvEN.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            binding.tvEN.setPadding(Functions.share.convertDpToPixel(7f, getContext()), Functions.share.convertDpToPixel(2f, getContext()), Functions.share.convertDpToPixel(7f, getContext()), Functions.share.convertDpToPixel(2f, getContext()));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Functions.share.convertDpToPixel(2f, getContext()), 0, 0, 0);
            binding.tvEN.setLayoutParams(params);
            binding.tvEN.setEnabled(false);
        }
    }

    private void loadInfo() {
        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
        binding.tvUsername.setText(CurrentUser.getInstance().getUser().getFullName());
        binding.tvEmail.setText(CurrentUser.getInstance().getUser().getEmail());

        if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
            binding.tvDepartment.setText(CurrentUser.getInstance().getUser().getDepartmentTitle());
        } else {
            binding.tvDepartment.setText(CurrentUser.getInstance().getUser().getDepartmentTitleEN());
        }

        binding.tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = Functions.share.getScreenHeight() * 94 / 100;;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.addBottomSheetCallback(new MyBottomSheetBehavior());
    }

    private void switchLanguage(int localeId) {
        sharedPreferencesController.saveLocaleId(localeId + "");
        CurrentUser.getInstance().getUser().setDefaultLanguageid(localeId);
        sharedPreferencesController.saveUser(new Gson().toJson(CurrentUser.getInstance().getUser()));

        Intent i = new Intent(getActivity(), TabActivity.class);
        getActivity().startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvClose) {
            dismiss();
        } else if (v == binding.lnLogout) {
            Utility.share.showAlertWithOKCancel(getActivity().getString(R.string.signout_notify), getActivity(), () -> {
                presenter.signOut(AccountFragment.this);
            });
        } else if (v == binding.rlProfile) {
            AnimationController.share.fadeIn(binding.imgAvatar, getContext());
            ProfileFragment bottomSheetAccountDialog = new ProfileFragment();
            bottomSheetAccountDialog.show(getParentFragmentManager(), ProfileFragment.TAG);
        } else if (v == binding.lnComment) {
            Intent i = new Intent(getActivity(), CommentActivity.class);
            getActivity().startActivity(i);
        } else if (v == binding.rlInteractive) {
            Intent i = new Intent(getActivity(), InteractiveActivity.class);
            getActivity().startActivity(i);
        } else if (v == binding.rlConfig) {
            Intent i = new Intent(getActivity(), ConfigNotificationActivity.class);
            startActivity(i);
        } else if (v == binding.tvEN) {
            switchLanguage(1033);
        } else if (v == binding.tvVN) {
            switchLanguage(1066);
        }
    }

    @Override
    public void onPause() {
        behavior.removeBottomSheetCallback(new MyBottomSheetBehavior());
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        DCMApplication.getInstance().trackScreenView("Account screen");
    }

    class MyBottomSheetBehavior extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    }
}
