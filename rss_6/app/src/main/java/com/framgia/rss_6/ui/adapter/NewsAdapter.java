package com.framgia.rss_6.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsModel> mNewsModels;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ItemClickListener mClickListener;

    public NewsAdapter(Context context, List<NewsModel> datas) {
        mContext = context;
        mNewsModels = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_list_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindData(mNewsModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsModels != null ? mNewsModels.size() : 0;
    }

    public void setClickListener(NewsAdapter.ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_title)
        TextView mTextView;
        @BindView(R.id.text_publish_date)
        TextView mTextPubdate;
        @BindView(R.id.image_news)
        ImageView mImageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindData(NewsModel news) {
            if (news == null) return;
            mTextView.setText(news.getTitle());
            mTextPubdate.setText(news.getPubDate());
            Picasso.with(mContext).load(news.getImage()).into(mImageView);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onClick(view, getAdapterPosition());
        }
    }
}