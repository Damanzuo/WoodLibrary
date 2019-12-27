package com.wood.library.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wood.library.bean.Banner;

import java.util.List;

public class AutoBannerAdapter<T extends Banner> extends RecyclerView.Adapter<AutoBannerAdapter.ViewHolder> {

    private List<T> mData;

    public AutoBannerAdapter(List<T> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(params);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoBannerAdapter.ViewHolder holder, int position) {
        holder.bindViewData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindViewData(T t) {
            Glide.with(itemView)
                    .load(t.getImageUrl())
                    .into((ImageView) itemView);
        }
    }
}
