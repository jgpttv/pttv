package com.cch.gsyvideoplayer.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.bean.TvChannelInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvChannelAdapter extends RecyclerView.Adapter<TvChannelAdapter.TvChannelHolder> implements View.OnClickListener {
    private final List<TvChannelInfo> datas;
    private final Context context;
    private OnItemClickListener listener;

    public TvChannelAdapter(Context context, List<TvChannelInfo> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public TvChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel, null);
        view.setOnClickListener(this);
        return new TvChannelHolder(view);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null && listener != null) {
            listener.onItemClick((TvChannelInfo)tag);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TvChannelHolder holder, int position) {
        TvChannelInfo info = datas.get(position);
        holder.itemView.setTag(info);
        holder.tv_name.setText(info.getName());
        Glide.with(context)
                .load(info.getIcon())
                .into(holder.iv_icon);
    }

    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(TvChannelInfo info);
    }

    public class TvChannelHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;

        public TvChannelHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
