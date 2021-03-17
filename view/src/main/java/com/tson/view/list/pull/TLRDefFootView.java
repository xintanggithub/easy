package com.tson.view.list.pull;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tson.view.R;

public class TLRDefFootView extends LinearLayout implements TLRUIHandler {
    private boolean isMoveUp = false;
    private ImageView mImageView;
    private TextView mTextView;
    private ValueAnimator mReleaseAnimator;
    private AnimationDrawable mAnimationDrawable;

    private String before, loading, push, end;

    public void setText(String before, String push, String loading, String end) {
        this.before = before;
        this.loading = loading;
        this.push = push;
        this.end = end;
    }

    public TLRDefFootView(Context context) {
        this(context, null);
    }

    public TLRDefFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TLRDefFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.tlr_def_foot_layout, this);
        setWillNotDraw(true);
        mImageView = (ImageView) findViewById(R.id.tlr_def_icon);
        mTextView = (TextView) findViewById(R.id.tlr_def_text);
        initReleaseAnimator();
    }

    private void initReleaseAnimator() {
        mReleaseAnimator = ValueAnimator.ofFloat(0, 180);
        mReleaseAnimator.setDuration(210);
        mReleaseAnimator.setInterpolator(new LinearInterpolator());
        mReleaseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mImageView.setRotation(value);
            }
        });
    }

    public void setTextView(CharSequence sequence) {
        if (mTextView != null) {
            mTextView.setText(sequence);
        }
    }

    @Override
    public void onRefreshStatusChanged(View target, TLRLinearLayout.RefreshStatus status) {

    }

    @Override
    public void onLoadStatusChanged(View target, TLRLinearLayout.LoadStatus status) {
        switch (status) {
            case PULL_UP:
                if (isMoveUp) {
                    if (null != before && !TextUtils.isEmpty(before)) {
                        mTextView.setText(before);
                    } else {
                        mTextView.setText(R.string.tlr_def_foot_pull_up);
                    }
                }
                break;
            case RELEASE_LOAD:
                if (null != push && !TextUtils.isEmpty(push)) {
                    mTextView.setText(push);
                } else {
                    mTextView.setText(R.string.tlr_def_foot_release_load);
                }
                mReleaseAnimator.start();
                break;
            case LOADING:
                if (mReleaseAnimator.isRunning()) {
                    mReleaseAnimator.end();
                }
                mImageView.setImageResource(R.drawable.tlr_def_refresh_loading);
                mAnimationDrawable = (AnimationDrawable) mImageView.getDrawable();
                mAnimationDrawable.start();
                if (null != loading && !TextUtils.isEmpty(loading)) {
                    mTextView.setText(loading);
                } else {
                    mTextView.setText(R.string.tlr_def_foot_loading);
                }
                break;
            case IDLE:
                break;
        }
    }

    @Override
    public void onOffsetChanged(View target, boolean isRefresh, int totalOffsetY, int totalThresholdY, int offsetY, float threshOffset) {
        isMoveUp = offsetY < 0;
        if (threshOffset == 0) {
            mImageView.setRotation(0);
        }
    }

    @Override
    public void onFinish(View target, boolean isRefresh, boolean isSuccess, int errorCode) {
        if (null != end && !TextUtils.isEmpty(end)) {
            mTextView.setText(end);
        } else {
            mTextView.setText(R.string.tlr_def_foot_load_complete);
        }
        if (!isRefresh) {
            try {
                mAnimationDrawable.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mImageView.setRotation(180);
            mImageView.setImageResource(R.drawable.tlr_def_load);
        }
    }
}
