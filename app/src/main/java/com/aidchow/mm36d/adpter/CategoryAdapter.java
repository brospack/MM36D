package com.aidchow.mm36d.adpter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aidchow.entity.FindFlowerEntity;
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
 * Created by 78537 on 2017/4/9.
 */

public class CategoryAdapter extends BaseQuickAdapter<FindFlowerEntity, BaseViewHolder> {
    List<FindFlowerEntity> findFlowerEntities;

    public CategoryAdapter(List<FindFlowerEntity> data) {
        super(R.layout.category_item, data);
        this.findFlowerEntities = data;
    }


    @Override
    protected void convert(final BaseViewHolder helper, FindFlowerEntity item) {
        helper.setText(R.id.tv_title, item.getCategory() + "\n" + item.getTotal());
        Glide.with(App.getContext())
                .load(item.getPreviewUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ((ScaleImageView) helper.getView(R.id.image_view_mm)).setInitSize(resource.getWidth(), resource.getHeight());
                        ((ScaleImageView) helper.getView(R.id.image_view_mm)).setImageBitmap(resource);
                    }
                });

    }

    public void setData(List<FindFlowerEntity> findFlowerEntities) {
        this.findFlowerEntities.addAll(findFlowerEntities);
    }

    public List<FindFlowerEntity> getFindFlowerEntities() {
        return findFlowerEntities;
    }

}
