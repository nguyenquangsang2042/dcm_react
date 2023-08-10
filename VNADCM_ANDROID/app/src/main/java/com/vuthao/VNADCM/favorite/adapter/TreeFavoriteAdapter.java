package com.vuthao.VNADCM.favorite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.category.adapter.TreeCategoryAdapter;
import com.vuthao.VNADCM.databinding.ItemTreeCategoryBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class TreeFavoriteAdapter extends RecyclerView.Adapter<TreeFavoriteAdapter.TreeFavoriteHolder> {
    private Context context;
    private final ArrayList<FavoriteFolder> favoriteFolders;
    private final TreeFavoriteListener listener;

    public interface TreeFavoriteListener {
        void onTreeItemClick(int pos);
    }

    public TreeFavoriteAdapter(Context context, ArrayList<FavoriteFolder> favoriteFolders, TreeFavoriteListener listener) {
        this.context = context;
        this.favoriteFolders = favoriteFolders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TreeFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTreeCategoryBinding binding = ItemTreeCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TreeFavoriteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeFavoriteHolder holder, int position) {
        FavoriteFolder favoriteFolder = favoriteFolders.get(position);

        if (position == favoriteFolders.size() - 1) {
            holder.binding.viewLine.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.viewLine.setVisibility(View.VISIBLE);
        }

        if (favoriteFolder != null) {
            if (!Functions.isNullOrEmpty(favoriteFolder.getTitle())) {
                holder.binding.tvTitle.setText(favoriteFolder.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            holder.binding.imgThumb.setImageResource(R.drawable.icon_folder_favorite);
        }
    }

    @Override
    public int getItemCount() {
        return favoriteFolders.size();
    }

    public class TreeFavoriteHolder extends RecyclerView.ViewHolder {
        private ItemTreeCategoryBinding binding;

        public TreeFavoriteHolder(@NonNull ItemTreeCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onTreeItemClick(getAdapterPosition()));
        }
    }
}
