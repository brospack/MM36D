package com.aidchow.mm36d.imagedetail;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageButton;

import com.aidchow.mm36d.R;
import com.aidchow.mm36d.listener.CustomTransitionListener;
import com.aidchow.mm36d.ui.widget.ScaleImageView;
import com.aidchow.mm36d.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class ImageDetailActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION_SHORT = 150;
    private static final int ANIMATION_DURATION_MEDIUM = 300;
    private static final int ANIMATION_DURATION_LONG = 450;
    private static final int ANIMATION_DURATION_EXTRA_LONG = 850;

    private ImageButton mFabWallsButton;
    private ImageButton mFabShareButton;
    private ImageButton mFabDownloadButton;

    private DonutProgress mFabProgess;

    private Drawable mDrawablePhoto;
    private Drawable mDrawableClose;
    private Drawable mDrawableSuccess;
    private Drawable mDrawableError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dtail);
        findViewById(R.id.relative_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String imageUrl = getIntent().getStringExtra("image_url");
        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ScaleImageView scaleImageView = (ScaleImageView) findViewById(R.id.image_view_mm);
                        scaleImageView.setInitSize(resource.getWidth(), resource.getHeight());
                        scaleImageView.setImageBitmap(resource);
                    }
                });
        mDrawablePhoto = getDrawable(R.drawable.ic_photo);
        mDrawableClose = getDrawable(R.drawable.ic_close);
        mDrawableSuccess = getDrawable(R.drawable.ic_success);
        mDrawableError = getDrawable(R.drawable.ic_error);

        mFabProgess = (DonutProgress) findViewById(R.id.activity_detail_progress);
        mFabProgess.setMax(100);
        mFabProgess.setScaleX(0);
        mFabProgess.setScaleY(0);

        mFabWallsButton = (ImageButton) findViewById(R.id.image_detail_fab_walls);
        mFabWallsButton.setScaleX(0);
        mFabWallsButton.setScaleY(0);
        mFabWallsButton.setImageDrawable(mDrawablePhoto);
        mFabWallsButton.setOnClickListener(onFabWallsButtonListener);

        mFabShareButton = (ImageButton) findViewById(R.id.image_detail_fab_share);
        mFabShareButton.setScaleX(0);
        mFabShareButton.setScaleY(0);
        mFabShareButton.setImageDrawable(getDrawable(R.drawable.ic_share));
        mFabShareButton.setOnClickListener(onFabShareButtonListener);

        mFabDownloadButton = (ImageButton) findViewById(R.id.image_detail_fab_download);
        mFabDownloadButton.setScaleX(0);
        mFabDownloadButton.setScaleY(0);
        mFabDownloadButton.setImageDrawable(getDrawable(R.drawable.ic_file_download));
        mFabDownloadButton
                .setOnClickListener(onFabDownloadButtonListener);

        animateActivityStart();
    }


    private void animateActivityStart() {
        Utils.showViewByScale(mFabWallsButton)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .start();
        Utils.showViewByScale(mFabShareButton)
                .setDuration(ANIMATION_DURATION_MEDIUM * 2)
                .start();
        mFabShareButton.animate()
                .translationX((-1) * Utils.dp2px(this, 58))
                .setStartDelay(ANIMATION_DURATION_MEDIUM)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .start();

        Utils.showViewByScale(mFabDownloadButton)
                .setDuration(ANIMATION_DURATION_MEDIUM * 2)
                .start();
        mFabDownloadButton.animate()
                .translationX((-1) * Utils.dp2px(this, 108))
                .setStartDelay(ANIMATION_DURATION_MEDIUM)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .start();
    }

    private View.OnClickListener onFabWallsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onFabShareButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onFabDownloadButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onBackPressed() {
        mFabDownloadButton.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .start();
        mFabShareButton.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .start();
        super.onBackPressed();
    }
}
