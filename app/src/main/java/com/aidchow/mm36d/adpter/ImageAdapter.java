package com.aidchow.mm36d.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aidchow.entity.ImageEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.ui.widget.ScaleImageView;
import com.aidchow.mm36d.util.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

/**
 * Created by 78537 on 2017/4/4.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {
    private Context mContext;
    private List<ImageEntity> imageEntityList;

    public ImageAdapter(List<ImageEntity> imageEntityList) {
        this.imageEntityList = imageEntityList;
    }

    public void setDatas(List<ImageEntity> imageEntityList) {
        this.imageEntityList = imageEntityList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        ImageEntity imageEntity = imageEntityList.get(position);
        holder.tvTitle.setText(imageEntity.getTitle().replace("(点击图片,更多精彩)", ""));
        holder.tvPushDate.setText(imageEntity.getPushDate());
        @SuppressLint("StringFormatMatches")
        String broseLikeNum = String.format(mContext.getString(R.string.browse_like_num),
                imageEntity.getBrowseNum(), imageEntity.getLikeNum());
        holder.tvBrowseLikeNum.setText(broseLikeNum);
        Glide.with(mContext)
                .load(imageEntity.getSmallImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        holder.mmImage.setInitSize(width, height);
                        holder.mmImage.setImageBitmap(resource);
                    }
                });
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle payload = (Bundle) payloads.get(0);
            ImageEntity imageEntity = imageEntityList.get(position);
            if (payload.getString("LIKE_NUM") != null || payload.getString("BROWSE_NUM") != null) {
                String browseLikeNum = String.format(mContext.getString(R.string.browse_like_num),
                        imageEntity.getBrowseNum(), imageEntity.getLikeNum());
                holder.tvBrowseLikeNum.setText(browseLikeNum);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageEntityList == null ? 0 : imageEntityList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ScaleImageView mmImage;
        TextView tvTitle;
        TextView tvPushDate;
        TextView tvBrowseLikeNum;

        public VH(View itemView) {
            super(itemView);
            mmImage = (ScaleImageView) itemView.findViewById(R.id.image_view_mm);
            tvPushDate = (TextView) itemView.findViewById(R.id.tv_push_date);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvBrowseLikeNum = (TextView) itemView.findViewById(R.id.tv_browse_like_num);
        }
    }
}
