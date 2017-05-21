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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 78537 on 2017/4/4.
 */

public class ImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> {

    private List<ImageEntity> imageEntityList;

    public ImageAdapter(List<ImageEntity> imageEntityList) {
        super(R.layout.image_item, imageEntityList);
        this.imageEntityList = imageEntityList;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ImageEntity item) {
        @SuppressLint("StringFormatMatches")
        String broseLikeNum = String.format(App.getContext().getString(R.string.browse_like_num),
                item.getBrowseNum(), item.getLikeNum());
        helper.setText(R.id.tv_title, item.getTitle().replace("(点击图片,更多精彩)", ""))
                .setText(R.id.tv_browse_like_num, broseLikeNum)
                .setText(R.id.tv_push_date, item.getPushDate());
        Glide.with(App.getContext())
                .load(item.getSmallImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        ScaleImageView imageView = helper.getView(R.id.image_view_mm);
                        imageView.setInitSize(width, height);
                        imageView.setImageBitmap(resource);
                    }
                });
    }


}
