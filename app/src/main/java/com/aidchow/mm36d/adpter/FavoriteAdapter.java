package com.aidchow.mm36d.adpter;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import com.aidchow.mm36d.App;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.favorite.FavoriteContract;
import com.aidchow.mm36d.imagedetaillist.ImageDetailListContract;
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

public class FavoriteAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private FavoriteContract.Presenter presenter;

    public FavoriteAdapter(List<String> data, FavoriteContract.Presenter presenter) {
        super(R.layout.detail_image_item, data);
        this.presenter = presenter;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        final ScaleImageView imageView = helper.getView(R.id.image_view_mm);
        ImageButton imageButton = helper.getView(R.id.image_fab_save);
        if (presenter.loadImage(item)) {
            imageButton.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_favorite));
        } else {
            imageButton.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_favorite_borde));
        }
        helper.addOnClickListener(R.id.image_fab_save);
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
