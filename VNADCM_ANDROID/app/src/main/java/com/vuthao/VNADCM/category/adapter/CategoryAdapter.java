package com.vuthao.VNADCM.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.ImageLoader;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.Image;
import com.vuthao.VNADCM.databinding.ItemCategoryBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private Context context;
    private ArrayList<DocumentAreaCategory> categories;
    private CategoryListener listener;

    public interface CategoryListener {
        void onItemClick(int pos);
    }

    public CategoryAdapter(Context context, ArrayList<DocumentAreaCategory> categories, CategoryListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        DocumentAreaCategory category = categories.get(position);
        if (category != null) {

            if (!Functions.isNullOrEmpty(category.getImage())) {
                Image image = Functions.jsonToObj(category.getImage(), Image.class);
                ImageLoader.getInstance().loadImageCategory(context, image.getPath(), holder.binding.imgThumb);
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_thumbnail_category_default);
            }

            if (CurrentUser.getInstance().getUser().getDefaultLanguageid() == 1066) {
                if (!Functions.isNullOrEmpty(category.getTitle())) {
                    holder.binding.tvName.setText(category.getTitle());
                } else {
                    holder.binding.tvName.setText("");
                }
            } else {
                if (!Functions.isNullOrEmpty(category.getTitleEN())) {
                    holder.binding.tvName.setText(category.getTitleEN());
                } else {
                    holder.binding.tvName.setText("");
                }
            }

            if (!Functions.isNullOrEmpty(category.getDescription())) {
                holder.binding.tvDescription.setText(category.getDescription());
            } else {
                holder.binding.tvDescription.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;
        public CategoryHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }
}
