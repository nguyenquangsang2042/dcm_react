package com.vuthao.VNADCM.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Nhum Lê Sơn Thạch on 11/01/2023.
 */
public class BaseFragment extends Fragment {
    protected BaseActivity sBaseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBaseActivity = (BaseActivity) requireActivity();
    }
}
