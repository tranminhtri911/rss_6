package com.framgia.rss_6.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.ChannelModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private List<ChannelModel> mChannelModels;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ItemClickListener mClickListener;

    public ChannelAdapter(Context context, List<ChannelModel> datas) {
        mContext = context;
        mChannelModels = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_list_channel, parent, false);
        return new ChannelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.bindData(mChannelModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mChannelModels != null ? mChannelModels.size() : 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_caterogy)
        TextView mTextCaterogy;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindData(ChannelModel news) {
            if (news == null) return;
            mTextCaterogy.setText(news.getCategory());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onClick(view, getAdapterPosition());
        }
    }
}