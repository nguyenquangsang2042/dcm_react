package com.vuthao.VNADCM.home.adapter;

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
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.databinding.ItemDocumentBinding;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentHolder> {
    private Context context;
    private ArrayList<Document> documents;
    private DocumentListener listener;

    public interface DocumentListener {
        void onItemClick(int pos);
    }

    public DocumentAdapter(Context context, ArrayList<Document> documents, DocumentListener listener) {
        this.context = context;
        this.documents = documents;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDocumentBinding binding = ItemDocumentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        int newWidth = Functions.share.getScreenWidth() - Functions.share.convertDpToPixel(60, context);
        newWidth = (newWidth / 5) * 2;
        binding.container.getLayoutParams().width = newWidth;
        return new DocumentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentHolder holder, int position) {
        Document document = documents.get(position);
        if (document != null) {

            if (!Functions.isNullOrEmpty(document.getThumbnail())) {
                ImageLoader.getInstance().loadImageDocument(context, document.getThumbnail(), holder.binding.imgThumb);
            } else {
                holder.binding.imgThumb.setImageResource(R.drawable.icon_document_default);
            }

            if (!Functions.isNullOrEmpty(document.getTitle())) {
                holder.binding.tvTitle.setText(document.getTitle());
            } else {
                holder.binding.tvTitle.setText("");
            }

            if (!Functions.isNullOrEmpty(document.getIssueDate())) {
                long l = Functions.share.formatStringToLong(document.getIssueDate(), Constants.formatDfDate);
                String date = Functions.share.formatLongToString(l, "dd/MM/yyyy");
                holder.binding.tvDate.setText(date);
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public class DocumentHolder extends RecyclerView.ViewHolder {
        private ItemDocumentBinding binding;
        public DocumentHolder(@NonNull ItemDocumentBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

            binding.getRoot().setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }
    }
}
