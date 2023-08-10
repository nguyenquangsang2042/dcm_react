package com.vuthao.VNADCM.interactive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.databinding.ItemDocumentInteractiveBinding;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;
import com.vuthao.VNADCM.home.adapter.DocumentAdapter;
import com.vuthao.VNADCM.notification.adapter.NotificationAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InteractiveAdapter extends RecyclerView.Adapter {
    private Context context;
    private DocumentInteractiveListener listener;
    private ArrayList<DocumentInteractive> documents;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    public interface DocumentInteractiveListener {
        void onItemClick(int pos);
    }

    public InteractiveAdapter(Context context, ArrayList<DocumentInteractive> documents, InteractiveAdapter.DocumentInteractiveListener listener) {
        this.context = context;
        this.documents = documents;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemDocumentInteractiveBinding binding = ItemDocumentInteractiveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DocumentInteractiveHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DocumentInteractiveLoadingHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InteractiveAdapter.DocumentInteractiveLoadingHolder) {

        } else {
            populateRows((InteractiveAdapter.DocumentInteractiveHolder) holder, position);
        }
    }

    private void populateRows(InteractiveAdapter.DocumentInteractiveHolder holder, int position) {
        DocumentInteractive item = documents.get(position);
        if (item != null) {
            if (position % 2 == 0) {
                holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
            } else {
                holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }

            if (!Functions.isNullOrEmpty(item.getThumbnail())) {
                ImageLoader.getInstance().loadImageDocument(context, item.getThumbnail(), holder.binding.imgThumb);
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_document_default);
            }

            if (!Functions.isNullOrEmpty(item.getTitle())) {
                holder.binding.tvTitle.setText(item.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            if (!Functions.isNullOrEmpty(item.getType())) {
                holder.binding.tvDes.setText(item.getType());
            } else {
                holder.binding.tvDes.setText("");
            }

            if (!Functions.isNullOrEmpty(item.getCreated())) {
                long l = Functions.share.formatStringToLong(item.getCreated(), Constants.formatDfDate);
                String date = Functions.share.formatLongToString(l, "HH:mm dd/MM/yyyy");
                holder.binding.tvDate.setText(date);
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return documents.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    public class DocumentInteractiveHolder extends RecyclerView.ViewHolder {
        ItemDocumentInteractiveBinding binding;

        public DocumentInteractiveHolder(@NonNull ItemDocumentInteractiveBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }

    public class DocumentInteractiveLoadingHolder extends RecyclerView.ViewHolder {

        public DocumentInteractiveLoadingHolder(@NonNull ItemLoadingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
