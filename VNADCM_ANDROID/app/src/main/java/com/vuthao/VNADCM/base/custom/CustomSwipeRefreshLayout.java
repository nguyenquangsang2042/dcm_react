package com.vuthao.VNADCM.base.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vuthao.VNADCM.R;

/**
 * Created by Nhum Lê Sơn Thạch on 17/02/2023.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    public CustomSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        setColorSchemeColors(ContextCompat.getColor(context, R.color.clPrimary));
    }

    public CustomSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(ContextCompat.getColor(context, R.color.clPrimary));
    }
}
