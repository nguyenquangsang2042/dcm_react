package com.vuthao.VNADCM.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.app.Image;
import com.vuthao.VNADCM.databinding.ItemDetailCategoryThumbBinding;
import com.vuthao.VNADCM.databinding.ItemDocumentBinding;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 14/02/2023.
 */
public class DetailCategoryThumbAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DocumentCategory> documentCategories;
    private DetailCategoryThumbListener listener;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    public interface DetailCategoryThumbListener {
        void onItemThumbClick(int pos);
    }

    public DetailCategoryThumbAdapter(Context context, ArrayList<DocumentCategory> documentCategories, DetailCategoryThumbListener listener) {
        this.context = context;
        this.documentCategories = documentCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemDetailCategoryThumbBinding binding = ItemDetailCategoryThumbBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            int newHeight = Functions.share.getScreenHeight() - Functions.share.convertDpToPixel(330, context);
            newHeight = (newHeight / 5) * 2;
            binding.imgThumb.getLayoutParams().height = newHeight;
            return new DetailCategoryThumbHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DetailCategoryThumbLoading(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailCategoryThumbLoading) {

        } else {
            populateItemRows((DetailCategoryThumbHolder) holder, position);
        }
    }

    private void populateItemRows(DetailCategoryThumbHolder holder, int position) {
        DocumentCategory documentCategory = documentCategories.get(position);
        if (documentCategory != null) {
            if (!Functions.isNullOrEmpty(documentCategory.getThumbnail())) {
                ImageLoader.getInstance().loadImageDocument(context, documentCategory.getThumbnail(), holder.binding.imgThumb);
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_document_default);
            }

            if (!Functions.isNullOrEmpty(documentCategory.getTitle())) {
                holder.binding.tvTitle.setText(documentCategory.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            if (!Functions.isNullOrEmpty(documentCategory.getIssueDate())) {
                long l = Functions.share.formatStringToLong(documentCategory.getIssueDate(), Constants.formatDfDate);
                String d = Functions.share.formatLongToString(l, "dd/MM/yyyy");
                holder.binding.tvDate.setText(d);
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return documentCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return documentCategories.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    public class DetailCategoryThumbHolder extends RecyclerView.ViewHolder {
        private ItemDetailCategoryThumbBinding binding;

        public DetailCategoryThumbHolder(@NonNull ItemDetailCategoryThumbBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemThumbClick(getAdapterPosition()));
        }
    }

    public class DetailCategoryThumbLoading extends RecyclerView.ViewHolder {

        public DetailCategoryThumbLoading(@NonNull ItemLoadingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
