package com.vuthao.VNADCM.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.Image;
import com.vuthao.VNADCM.databinding.ItemTreeCategoryBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 14/02/2023.
 */
public class TreeCategoryAdapter extends RecyclerView.Adapter<TreeCategoryAdapter.TreeCategoryHolder> {
    private Context context;
    private ArrayList<DocumentAreaCategory> categories;
    private TreeCategoryListener listener;

    public interface TreeCategoryListener {
        void onItemClick(int pos);
    }

    public TreeCategoryAdapter(Context context, ArrayList<DocumentAreaCategory> categories, TreeCategoryListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TreeCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTreeCategoryBinding binding = ItemTreeCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TreeCategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeCategoryHolder holder, int position) {
        DocumentAreaCategory category = categories.get(position);
        if (position == categories.size() - 1) {
            holder.binding.viewLine.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.viewLine.setVisibility(View.VISIBLE);
        }

        if (category != null) {

            if (!Functions.isNullOrEmpty(category.getImage())) {
                Image image = Functions.jsonToObj(category.getImage(), Image.class);
                if (image != null) {
                    ImageLoader.getInstance().loadImageCategory(context, image.getPath(), holder.binding.imgThumb);
                } else {
                    holder.binding.imgThumb.setImageResource(R.drawable.icon_thumbnail_category_default);
                }
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_thumbnail_category_default);
            }

            if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
                if (!Functions.isNullOrEmpty(category.getTitle())) {
                    holder.binding.tvTitle.setText(category.getTitle());
                } else {
                    holder.binding.tvTitle.setText("");
                }
            } else {
                if (!Functions.isNullOrEmpty(category.getTitleEN())) {
                    holder.binding.tvTitle.setText(category.getTitleEN());
                } else {
                    holder.binding.tvTitle.setText("");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class TreeCategoryHolder extends RecyclerView.ViewHolder {
        private ItemTreeCategoryBinding binding;

        public TreeCategoryHolder(@NonNull ItemTreeCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> {
                listener.onItemClick(getAdapterPosition());
            });
        }
    }
}
