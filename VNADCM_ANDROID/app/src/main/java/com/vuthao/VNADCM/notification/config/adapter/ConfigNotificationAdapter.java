package com.vuthao.VNADCM.notification.config.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.databinding.ItemConfigNotificationBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class ConfigNotificationAdapter extends RecyclerView.Adapter<ConfigNotificationAdapter.ConfigNotificationHolder> {
    private Context context;
    private ArrayList<ConfigureNotification> configNotifies;
    private MoreInfo moreInfo;
    private ConfigNotificationListener listener;

    public interface ConfigNotificationListener {
        void onCheckNotifyChange(int pos, boolean isCheck);

        void onCheckEmailChange(int pos, boolean isCheck);
    }

    public ConfigNotificationAdapter(Context context, ArrayList<ConfigureNotification> configNotifies, MoreInfo moreInfo, ConfigNotificationListener listener) {
        this.context = context;
        this.configNotifies = configNotifies;
        this.moreInfo = moreInfo;
        this.listener = listener;

        if (this.moreInfo == null) {
            this.moreInfo = new MoreInfo();
            this.moreInfo.setNotifyCategoryId(CurrentUser.getInstance().getUser().getNotifyCategoryId());
            this.moreInfo.setEmailCategoryId(CurrentUser.getInstance().getUser().getEmailCategoryId());
        }
    }

    @NonNull
    @Override
    public ConfigNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConfigNotificationBinding binding = ItemConfigNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ConfigNotificationHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigNotificationHolder holder, int position) {
        ConfigureNotification configNotify = configNotifies.get(position);

        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if (configNotify != null) {
            if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
                if (!Functions.isNullOrEmpty(configNotify.getTitle())) {
                    holder.binding.tvTitle.setText(configNotify.getTitle());
                } else {
                    holder.binding.tvTitle.setText("");
                }
            } else {
                if (!Functions.isNullOrEmpty(configNotify.getTitleEN())) {
                    holder.binding.tvTitle.setText(configNotify.getTitleEN());
                } else {
                    holder.binding.tvTitle.setText("");
                }
            }

            holder.binding.checkNotify.setEnabled((configNotify.getIsConfig() & 1) > 0);

            holder.binding.checkEmail.setEnabled((configNotify.getIsConfig() & 2) > 0);

            if (moreInfo.getNotifyCategoryId() > 0) {
                holder.binding.checkNotify.setChecked((moreInfo.getNotifyCategoryId() & configNotify.getID()) > 0);
            } else {
                holder.binding.checkNotify.setChecked(true);
            }

            if (moreInfo.getEmailCategoryId() > 0) {
                holder.binding.checkEmail.setChecked((moreInfo.getEmailCategoryId() & configNotify.getID()) > 0);
            } else {
                holder.binding.checkEmail.setChecked(true);
            }

            configNotify.setNotifyChecked(holder.binding.checkNotify.isChecked());
            configNotify.setEmailChecked(holder.binding.checkEmail.isChecked());

            holder.binding.checkNotify.setOnCheckedChangeListener(null);
            holder.binding.checkEmail.setOnCheckedChangeListener(null);

            holder.binding.checkNotify.setOnCheckedChangeListener((compoundButton, b) -> listener.onCheckNotifyChange(position, b));
            holder.binding.checkEmail.setOnCheckedChangeListener((compoundButton, b) -> listener.onCheckEmailChange(position, b));
        }
    }

    @Override
    public int getItemCount() {
        return configNotifies.size();
    }

    public class ConfigNotificationHolder extends RecyclerView.ViewHolder {
        private ItemConfigNotificationBinding binding;

        public ConfigNotificationHolder(@NonNull ItemConfigNotificationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
