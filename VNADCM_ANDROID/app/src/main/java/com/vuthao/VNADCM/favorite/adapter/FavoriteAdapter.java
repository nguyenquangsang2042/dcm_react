package com.vuthao.VNADCM.favorite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;
import com.vuthao.VNADCM.databinding.ItemFavoriteBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private Context context;
    private ArrayList<FavoriteFolder> favorites;
    private FavoriteListener listener;

    public interface FavoriteListener {
        void onItemClick(int pos);
    }

    public FavoriteAdapter(Context context, ArrayList<FavoriteFolder> favorites, FavoriteListener listener) {
        this.context = context;
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteBinding binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        FavoriteFolder favorite = favorites.get(position);
        if (favorite != null) {

            if (!Functions.isNullOrEmpty(favorite.getTitle())) {
                holder.binding.tvName.setText(favorite.getTitle());
            } else {
                holder.binding.tvName.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        private ItemFavoriteBinding binding;

        public FavoriteHolder(@NonNull ItemFavoriteBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }
}
