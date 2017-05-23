package com.aidchow.mm36d.imagedetail;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import com.aidchow.mm36d.R;
import com.aidchow.mm36d.listener.CustomAnmimatorListener;
import com.aidchow.mm36d.ui.widget.ScaleImageView;
import com.aidchow.mm36d.util.Logger;
import com.aidchow.mm36d.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class ImageDetailActivity extends AppCompatActivity implements ImageDetailContract.View {

    private static final String TAG = "ImageDetailActivity";
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

    private String imageUrl;

    private Animation mProgressFabAnimation;

    private ImageDetailContract.Presenter mPresenter;

    private boolean download = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dtail);
        findViewById(R.id.coordinator_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageUrl = getIntent().getStringExtra("image_url");
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

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter = new ImageDetailPresenter(this, this);
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
            if (!download) {
                download = true;
                animateStart();
                mFabWallsButton.animate().rotation(360)
                        .setDuration(ANIMATION_DURATION_LONG)
                        .start();

                mFabWallsButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.setImageWalls(imageUrl);
                    }
                }, 1000);
            } else {
                animateReset(false);
            }
        }
    };

    private View.OnClickListener onFabShareButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!download) {
                download = true;
                animateStart();
                mFabWallsButton.animate().rotation(360)
                        .setDuration(ANIMATION_DURATION_LONG)
                        .start();

                mFabWallsButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.shareImage(imageUrl);
                    }
                }, 1000);
            } else {
                animateReset(false);
            }
        }
    };

    private View.OnClickListener onFabDownloadButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ImageDetailActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ImageDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Snackbar.make(v, "我们需要这个权限保存文件", Snackbar.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(ImageDetailActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                } else {
                    ActivityCompat.requestPermissions(ImageDetailActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                }
            } else {
                download();
            }

        }
    };

    private void download() {
        if (!download) {
            download = true;
            animateStart();
            mFabWallsButton.animate().rotation(360)
                    .setDuration(ANIMATION_DURATION_LONG)
                    .start();

            mFabWallsButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.downloadImage(imageUrl);
                }
            }, 1000);
        } else {
            animateReset(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            } else {
                Snackbar.make(mFabDownloadButton, getString(R.string.permission_description), Snackbar.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        mFabDownloadButton.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .setListener(animatorListener1)
                .start();
        mFabShareButton.animate()
                .translationX(0)
                .setDuration(ANIMATION_DURATION_MEDIUM)
                .setListener(animatorListener1)
                .start();
    }

    private CustomAnmimatorListener animatorListener1 = new CustomAnmimatorListener() {
        private int animateFinish1 = 0;

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            process();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            process();
        }

        private void process() {
            animateFinish1 = animateFinish1 + 1;
            if (animateFinish1 >= 2) {
                Utils.hideViewByScaleXY(mFabDownloadButton)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animatorListener2)
                        .start();

                Utils.hideViewByScaleXY(mFabShareButton)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animatorListener2)
                        .start();

                Utils.hideViewByScaleXY(mFabWallsButton)
                        .setDuration(ANIMATION_DURATION_MEDIUM)
                        .setListener(animatorListener2)
                        .start();
                coolBack();
            }
        }
    };
    private CustomAnmimatorListener animatorListener2 = new CustomAnmimatorListener() {
        private int animateFinish2 = 0;

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            process();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            process();
        }

        private void process() {
            animateFinish2 = animateFinish2 + 1;
            if (animateFinish2 >= 3) {
                coolBack();
            }
        }
    };

    private void coolBack() {
        try {
            super.onBackPressed();
        } catch (Exception ignored) {
        }

    }

    @Override
    public void setPresenter(ImageDetailContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void downLoadComplete(final String filePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animateCompleteFirst(true);
                animateComplete(true);
            }
        });

    }

    @Override
    public void downLoadFail(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animateReset(true);
                Snackbar.make(mFabProgess, msg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showDownLoading(long downloaded, int total) {
        final int progress = (int) (downloaded * 100.0 / total);
        mFabProgess.post(new Runnable() {
            @Override
            public void run() {
                mFabProgess.setProgress(progress);
            }
        });
    }

    @Override
    public void setShare(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share_hint)), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            animateCompleteFirst(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void animateStart() {
        mFabProgess.setProgress(0);
        mFabShareButton.animate().translationX(0).setDuration(ANIMATION_DURATION_SHORT).start();
        mFabDownloadButton.animate().translationX(0).setDuration(ANIMATION_DURATION_SHORT).start();
        Utils.showViewByScale(mFabProgess).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mFabProgess.setProgress(1);
        mProgressFabAnimation = new RotateAnimation(0.0f, 360.0f,
                mFabProgess.getWidth() / 2, mFabProgess.getHeight() / 2);
        mProgressFabAnimation.setDuration(ANIMATION_DURATION_EXTRA_LONG * 2);
        mProgressFabAnimation.setInterpolator(new LinearInterpolator());
        mProgressFabAnimation.setRepeatCount(Animation.INFINITE);
        mProgressFabAnimation.setRepeatMode(-1);
        mFabWallsButton.setImageDrawable(mDrawableClose);

        if (mFabWallsButton.getTag() != null) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFabWallsButton.getBackground();
            transitionDrawable.reverseTransition(ANIMATION_DURATION_LONG);
            mFabWallsButton.setTag(null);
        }
        if (mFabShareButton.getTag() != null) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFabShareButton.getBackground();
            transitionDrawable.reverseTransition(ANIMATION_DURATION_LONG);
            mFabShareButton.setTag(null);
        }
        if (mFabDownloadButton.getTag() != null) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFabDownloadButton.getBackground();
            transitionDrawable.reverseTransition(ANIMATION_DURATION_LONG);
            mFabDownloadButton.setTag(null);
        }

    }

    private void animateReset(boolean error) {
        download = false;
        mPresenter.downloadCancel();
        //animating everything back to default :D
        Utils.hideViewByScaleXY(mFabProgess).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mProgressFabAnimation.cancel();
        //Utils.animateViewElevation(mFabButton, 0, mElavationPx);

        if (error) {
            mFabWallsButton.setImageDrawable(mDrawableError);
        } else {
            mFabWallsButton.setImageDrawable(mDrawablePhoto);
        }

        mFabWallsButton.animate().rotation(360).setDuration(ANIMATION_DURATION_MEDIUM).start();

        mFabShareButton.animate().translationX((-1) * Utils.dp2px(this, 58)).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mFabDownloadButton.animate().translationX((-1) * Utils.dp2px(this, 108)).setDuration(ANIMATION_DURATION_MEDIUM).start();
    }

    /**
     * animate the first parts of the UI after the download has successfully finished
     */
    private void animateCompleteFirst(boolean success) {
        //some nice animations so the user knows the wallpaper was set properly
        mFabWallsButton.animate().rotation(720).setDuration(ANIMATION_DURATION_EXTRA_LONG).start();
        mFabWallsButton.setImageDrawable(mDrawableSuccess);

        //animate the button to green. just do it the first time
        if (mFabWallsButton.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabWallsButton.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabWallsButton.setTag("");
        }

        if (mFabShareButton.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabShareButton.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabShareButton.setTag("");
        }

        if (mFabDownloadButton.getTag() == null) {
            TransitionDrawable transition = (TransitionDrawable) mFabDownloadButton.getBackground();
            transition.startTransition(ANIMATION_DURATION_LONG);
            mFabDownloadButton.setTag("");
        }
    }


    private void animateComplete(boolean success) {
        //hide the progress again :D
        Utils.hideViewByScaleXY(mFabProgess).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mProgressFabAnimation.cancel();

        //show the fab again ;)
        mFabShareButton.animate().translationX((-1) * Utils.dp2px(this, 58)).setDuration(ANIMATION_DURATION_MEDIUM).start();
        mFabDownloadButton.animate().translationX((-1) * Utils.dp2px(this, 108)).setDuration(ANIMATION_DURATION_MEDIUM).start();

        // if we were not successful remove the x again :D
        if (!success) {

            mFabShareButton.setImageDrawable(mDrawablePhoto);
            mFabShareButton.animate().rotation(360).setDuration(ANIMATION_DURATION_MEDIUM).start();
        }
        download = false;
        mPresenter.downloadCancel();
    }

}
