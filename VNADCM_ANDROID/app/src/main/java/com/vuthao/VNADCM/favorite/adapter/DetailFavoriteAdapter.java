package com.vuthao.VNADCM.favorite.adapter;

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
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.databinding.ItemDetailFavoriteBinding;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class DetailFavoriteAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DocumentFavorite> documentFavorites;
    private DetailFavoriteListener listener;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    public interface DetailFavoriteListener {
        void onDetailItemClick(int pos);
    }

    public DetailFavoriteAdapter(Context context, ArrayList<DocumentFavorite> documentFavorites, DetailFavoriteListener listener) {
        this.context = context;
        this.documentFavorites = documentFavorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemDetailFavoriteBinding binding = ItemDetailFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DetailFavoriteHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DetailFavoriteLoadingHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailFavoriteLoadingHolder) {
            
        } else {
            populateItemRows((DetailFavoriteHolder) holder, position);
        }
    }

    private void populateItemRows(DetailFavoriteHolder holder, int position) {
        DocumentFavorite documentFavorite = documentFavorites.get(position);

        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if (documentFavorite != null) {

            if (!Functions.isNullOrEmpty(documentFavorite.getThumbnail())) {
                ImageLoader.getInstance().loadImageDocument(context, documentFavorite.getThumbnail(), holder.binding.imgThumb);
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_document_default);
            }

            if (!Functions.isNullOrEmpty(documentFavorite.getResourceTitle())) {
                holder.binding.tvTitle.setText(documentFavorite.getResourceTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            if (!Functions.isNullOrEmpty(documentFavorite.getCreated())) {
                long l = Functions.share.formatStringToLong(documentFavorite.getCreated(), Constants.formatDfDate);
                String d = Functions.share.formatLongToString(l, "dd/MM/yyyy");
                holder.binding.tvDate.setText(d);
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return documentFavorites.size();
    }

    @Override
    public int getItemViewType(int position) {
        return documentFavorites.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    public class DetailFavoriteHolder extends RecyclerView.ViewHolder {
        private ItemDetailFavoriteBinding binding;
        public DetailFavoriteHolder(@NonNull ItemDetailFavoriteBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> {
                listener.onDetailItemClick(getAdapterPosition());
            });
        }
    }

    public class DetailFavoriteLoadingHolder extends RecyclerView.ViewHolder {

        public DetailFavoriteLoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
