package com.vuthao.VNADCM.category.adapter;

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
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.databinding.ItemDetailCategoryBinding;
import com.vuthao.VNADCM.databinding.ItemDocumentBinding;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 14/02/2023.
 */
public class DetailCategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<DocumentCategory> documentCategories;
    private DetailCategoryListener listener;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    public interface DetailCategoryListener {
        void onItemDetailCategoryClick(int pos);
    }

    public DetailCategoryAdapter(Context context, ArrayList<DocumentCategory> documentCategories, DetailCategoryListener listener) {
        this.context = context;
        this.documentCategories = documentCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemDetailCategoryBinding binding = ItemDetailCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DetailCategoryHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DetailCategoryLoadingHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailCategoryLoadingHolder) {

        } else {
            populateItemRows((DetailCategoryHolder) holder, position);
        }
    }

    private void populateItemRows(DetailCategoryHolder holder, int position) {
        DocumentCategory documentCategory = documentCategories.get(position);

        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

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

            if (!Functions.isNullOrEmpty(documentCategory.getCode())) {
                holder.binding.tvDes.setText(documentCategory.getCode());
            } else {
                holder.binding.tvDes.setText("");
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

    public class DetailCategoryHolder extends RecyclerView.ViewHolder {
        private ItemDetailCategoryBinding binding;

        public DetailCategoryHolder(@NonNull ItemDetailCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemDetailCategoryClick(getAdapterPosition()));
        }
    }

    public class DetailCategoryLoadingHolder extends RecyclerView.ViewHolder {

        public DetailCategoryLoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
