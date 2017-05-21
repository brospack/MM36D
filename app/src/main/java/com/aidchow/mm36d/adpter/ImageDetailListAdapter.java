package com.aidchow.mm36d.adpter;

import android.graphics.Bitmap;

import com.aidchow.mm36d.App;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.ui.widget.ScaleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 78537 on 2017/4/26.
 */

public class ImageDetailListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageDetailListAdapter(List<String> data) {
        super(R.layout.detail_image_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        final ScaleImageView imageView = helper.getView(R.id.image_view_mm);
        Glide.with(App.getContext())
                .load(item)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setInitSize(resource.getWidth(), resource.getHeight());
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
