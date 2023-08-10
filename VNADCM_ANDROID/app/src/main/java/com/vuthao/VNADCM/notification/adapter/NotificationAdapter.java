package com.vuthao.VNADCM.notification.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DateTimeUtility;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;
import com.vuthao.VNADCM.databinding.ItemNotificationBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class NotificationAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<Notify> notifies;
    private final NotificationListener listener;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;
    private static final String TAG = NotificationAdapter.class.getSimpleName();

    public interface NotificationListener {
        void onItemClick(int pos);
    }

    public NotificationAdapter(Context context, ArrayList<Notify> notifies, NotificationListener listener) {
        this.context = context;
        this.notifies = notifies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NotificationHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NotificationLoadingHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationLoadingHolder) {
            
        } else {
            populateRows((NotificationHolder) holder, position);
        }
    }

    private void populateRows(NotificationHolder holder, int position) {
        Notify notify = notifies.get(position);

        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if (notify != null) {
            holder.binding.imgAvatar.setImageResource(R.drawable.icon_list_notify);

            if (notify.isFlgRead()) {
                holder.binding.tvTitle.setTypeface(Typeface.DEFAULT);
            } else {
                holder.binding.tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            }

            if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
                if (!Functions.isNullOrEmpty(notify.getContent())) {
                    String newText = convert2Html(notify.getContent());
                    holder.binding.tvTitle.setText(Html.fromHtml(newText, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.binding.tvTitle.setText("");
                }
            } else {
                if (!Functions.isNullOrEmpty(notify.getContentEN())) {
                    String newText = convert2Html(notify.getContentEN());
                    holder.binding.tvTitle.setText(Html.fromHtml(newText, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.binding.tvTitle.setText("");
                }
            }

            if (!Functions.isNullOrEmpty(notify.getActionTime())) {
                long l = Functions.share.formatStringToLong(notify.getActionTime(), Constants.formatDfDate);
                String newTime = DateTimeUtility.formatTimeNotification(l);
                holder.binding.tvTime.setText(newTime);
            } else {
                holder.binding.tvTime.setText("");
            }
        }
    }

    private String convert2Html(String html) {
        StringBuilder newText = new StringBuilder();
        try {
            html = html.replace("<div style=\"color:#0072c6; line-height:20px\">", "");
            Element doc = Jsoup.parse(html);
            Elements elements = doc.select("body *");
            for (Element element: elements) {
                Elements e = element.getElementsByClass("textAction");
                if (e.size() > 0) {
                    newText.append("<font color='#0040A2'>")
                            .append(e.text())
                            .append("</font>")
                            .append(" ");
                } else {
                    newText.append(element.outerHtml())
                            .append(" ");
                }
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.getMessage());
        }

        return newText.toString();
    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return notifies.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        public NotificationHolder(@NonNull ItemNotificationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> {
                listener.onItemClick(getAdapterPosition());
            });
        }
    }

    public static class NotificationLoadingHolder extends RecyclerView.ViewHolder {

        public NotificationLoadingHolder(@NonNull ItemLoadingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
