package com.vuthao.VNADCM.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.SearchHistory;
import com.vuthao.VNADCM.databinding.ItemSearchHistoryBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder> {
    private Context context;
    private ArrayList<SearchHistory> searchHistories;
    private SearchHistoryListener listener;

    public interface SearchHistoryListener {
        void onItemHistoryClick(int pos);
        void onItemHistoryDeleteClick(int pos);
    }

    public SearchHistoryAdapter(Context context, ArrayList<SearchHistory> searchHistories, SearchHistoryListener listener) {
        this.context = context;
        this.searchHistories = searchHistories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchHistoryBinding binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchHistoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryHolder holder, int position) {
        SearchHistory searchHistory = searchHistories.get(position);
        if (searchHistory != null) {

            if (!Functions.isNullOrEmpty(searchHistory.getTitle())) {
                holder.binding.tvTitle.setText(searchHistory.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return searchHistories.size();
    }

    public class SearchHistoryHolder extends RecyclerView.ViewHolder {
        private ItemSearchHistoryBinding binding;

        public SearchHistoryHolder(@NonNull ItemSearchHistoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.tvTitle.setOnClickListener(view -> listener.onItemHistoryClick(getAdapterPosition()));
            binding.imgDelete.setOnClickListener(view -> listener.onItemHistoryDeleteClick(getAdapterPosition()));
        }
    }
}
