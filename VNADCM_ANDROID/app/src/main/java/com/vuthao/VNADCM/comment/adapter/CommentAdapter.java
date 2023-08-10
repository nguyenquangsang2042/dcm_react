package com.vuthao.VNADCM.comment.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Html;
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
import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.comment.presenter.CommentPresenter;
import com.vuthao.VNADCM.databinding.ItemCommentBinding;
import com.vuthao.VNADCM.databinding.ItemLoadingBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class CommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Comment> comments;
    private CommentListener listener;
    private final int TYPE_ITEM = 0;
    private final int TYPE_LOADING = 1;

    public interface CommentListener {
        void onItemClick(int pos);
    }

    public CommentAdapter(Context context, ArrayList<Comment> comments, CommentListener listener) {
        this.context = context;
        this.comments = comments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CommentHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CommentLoadingHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentLoadingHolder) {

        } else {
            populateRows((CommentHolder) holder, position);
        }
    }

    private void populateRows(CommentHolder holder, int position) {
        Comment comment = comments.get(position);

        if (position % 2 == 0) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.clBlue2));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if (comment != null) {

            if (!Functions.isNullOrEmpty(comment.getThumbnail())) {
                ImageLoader.getInstance().loadImageDocument(context, comment.getThumbnail(), holder.binding.imgThumb);
            } else  {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_document_default);
            }

            if (!Functions.isNullOrEmpty(comment.getTitle())) {
                holder.binding.tvTitle.setText(comment.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            if (!Functions.isNullOrEmpty(comment.getContent())) {
                holder.binding.tvDes.setText(Html.fromHtml(comment.getContent(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.binding.tvDes.setText("");
            }

            if (!Functions.isNullOrEmpty(comment.getCreated())) {
                long l = Functions.share.formatStringToLong(comment.getCreated(), Constants.formatDfDate);
                String date = Functions.share.formatLongToString(l, "HH:mm dd/MM/yyyy");
                holder.binding.tvDate.setText(date);
            } else {
                holder.binding.tvDate.setText("");
            }

            holder.binding.tvStatus.setBackgroundTintList(ColorStateList.valueOf(CommentPresenter.getColorById(context, comment)));
            holder.binding.tvStatus.setText(CommentPresenter.getTitleById(context, comment));
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return comments.get(position) == null ? TYPE_LOADING : TYPE_ITEM;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding binding;
        public CommentHolder(@NonNull ItemCommentBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }

    public class CommentLoadingHolder extends RecyclerView.ViewHolder {

        public CommentLoadingHolder(@NonNull ItemLoadingBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
