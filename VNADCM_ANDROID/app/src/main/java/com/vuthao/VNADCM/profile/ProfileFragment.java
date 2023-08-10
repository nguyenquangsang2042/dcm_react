package com.vuthao.VNADCM.profile;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.activity.BaseBottomSheetFragment;
import com.vuthao.VNADCM.base.activity.BaseFragment;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.databinding.FragmentProfileBinding;

public class ProfileFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    public static final String TAG = "BottomSheetProfileDialog";
    private FragmentProfileBinding binding;
    private BottomSheetBehavior behavior;

    public ProfileFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        init();

        binding.lblAvatar.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.tvBack.setOnClickListener(this);
        binding.tvClose.setOnClickListener(this);

        return binding.getRoot();
    }

    private void init() {
        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgAvatar);
        ImageLoader.getInstance().loadImageUser(getContext(), CurrentUser.getInstance().getUser().getImagePath(), binding.imgChangeAvatar);
        binding.tvUsername.setText(CurrentUser.getInstance().getUser().getFullName());
        binding.tvEmail.setText(CurrentUser.getInstance().getUser().getEmail());
        binding.tvContentEmail.setText(CurrentUser.getInstance().getUser().getEmail());
        binding.tvContentPhone.setText(CurrentUser.getInstance().getUser().getMobile());

        if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
            binding.tvContentPosition.setText(CurrentUser.getInstance().getUser().getPositionTitle());
        } else {
            binding.tvContentPosition.setText(CurrentUser.getInstance().getUser().getPositionTitleEN());
        }
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = Functions.share.getScreenHeight() * 94 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.addBottomSheetCallback(new MyBottomSheetBehavior());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            dismiss();
        } else if (v == binding.lblAvatar) {
            binding.imgBack.performClick();
        } else if (v == binding.tvClose) {
            binding.imgBack.performClick();
        } else if (v == binding.tvBack) {
            binding.imgBack.performClick();
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
        DCMApplication.getInstance().trackScreenView("Profile screen");
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