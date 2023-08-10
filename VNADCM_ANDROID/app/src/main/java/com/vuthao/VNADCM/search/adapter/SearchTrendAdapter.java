package com.vuthao.VNADCM.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vuthao.VNADCM.base.model.app.SearchTrend;
import com.vuthao.VNADCM.databinding.ItemSearchTrendBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class SearchTrendAdapter extends RecyclerView.Adapter<SearchTrendAdapter.SearchTrendHolder> {
    private Context context;
    private ArrayList<SearchTrend> searchTrends;
    private SearchTrendListener listener;

    public interface SearchTrendListener {
        void onSearchTrendClick(int pos);
    }

    public SearchTrendAdapter(Context context, ArrayList<SearchTrend> searchTrends, SearchTrendListener listener) {
        this.context = context;
        this.searchTrends = searchTrends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchTrendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchTrendBinding binding = ItemSearchTrendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchTrendHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTrendHolder holder, int position) {
        SearchTrend searchTrend = searchTrends.get(position);
        if (searchTrend != null) {
            holder.binding.tvTitle.setText(searchTrend.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return searchTrends.size();
    }

    public class SearchTrendHolder extends RecyclerView.ViewHolder {
        private ItemSearchTrendBinding binding;

        public SearchTrendHolder(@NonNull ItemSearchTrendBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onSearchTrendClick(getAdapterPosition()));
        }
    }
}
