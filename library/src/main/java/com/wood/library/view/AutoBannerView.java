package com.wood.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.wood.library.R;
import com.wood.library.adapter.AutoBannerAdapter;
import com.wood.library.animation.ZoomOutPageTransformer;
import com.wood.library.bean.Banner;

import java.util.ArrayList;
import java.util.List;

public class AutoBannerView<T extends Banner> extends RelativeLayout {

    private int mBannerLayoutResId;
    private boolean isAutoPlay;
    private int mDelayTime;
    private List<T> mBannerList = new ArrayList<>();
    private ViewPager2 mBannerView;
    private LinearLayout mIndicatorLayout;
    private TextView mBannerTitle;
    private AutoBannerAdapter<T> mAdapter;
    private Handler mHandler;
    private int mCurrentPosition = 1;
    private int mScrollTime=2000;

    public AutoBannerView(Context context) {
        this(context, null);
    }

    public AutoBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoBannerView);

        mBannerLayoutResId = typedArray.getResourceId(R.styleable.AutoBannerView_banner_layout, R.layout.banner_style_default);
        isAutoPlay = typedArray.getBoolean(R.styleable.AutoBannerView_is_auto_play, true);
        mDelayTime = typedArray.getInt(R.styleable.AutoBannerView_delayTime, 3000);

        typedArray.recycle();

        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(mBannerLayoutResId, this, true);

        mBannerTitle = findViewById(R.id.banner_title);
        mIndicatorLayout = findViewById(R.id.banner_indicator);
        mBannerView = findViewById(R.id.banner_view);
        if (mBannerView == null) {
            throw new NullPointerException("AutoBannerView child no bannerview");
        }
        initBannerView();
    }

    private void initBannerView() {
        mAdapter = new AutoBannerAdapter<>(mBannerList);
        mBannerView.setAdapter(mAdapter);
        mBannerView.setPageTransformer(new ZoomOutPageTransformer());
//        try {
//            Field mField = ViewPager2.class.getDeclaredField("mSavedItemAnimator");
//            mField.setAccessible(true);
//            RecyclerView.ItemAnimator itemAnimator = new RecyclerView.ItemAnimator();
//            mField.set();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
        mBannerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPosition = position;
                if (mBannerTitle != null) {
                    mBannerTitle.setText(mBannerList.get(position).getTitle());
                }
                for (int i = 0; i < mIndicatorLayout.getChildCount(); i++) {
                    ImageView childAt = (ImageView) mIndicatorLayout.getChildAt(i);
                    childAt.setImageResource(i == position - 1 ? R.drawable.shape_banner_indicator_selected :
                            R.drawable.shape_banner_indicator_normal);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mCurrentPosition == 0) {
                        mBannerView.setCurrentItem(mBannerList.size() - 2, false);//切换，不要动画效果
                    } else if (mCurrentPosition == mBannerList.size() - 1) {
                        mBannerView.setCurrentItem(1, false);//切换，不要动画效果
                    }
                }
            }
        });
    }


    public void setBannerData(@NonNull List<T> data) {
        mBannerList.clear();
        mBannerList.add(data.get(data.size() - 1));
        mBannerList.addAll(data);
        mBannerList.add(data.get(0));
        notifySetDataChanged();
    }

    private void notifySetDataChanged() {
        if (mBannerList.isEmpty()) {
            return;
        }
        addIndicator();
        mBannerView.setCurrentItem(mCurrentPosition, false);
        if (isAutoPlay) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(mAutoRunnable, mDelayTime);
        }
    }

    private Runnable mAutoRunnable = new Runnable() {
        @Override
        public void run() {
            mCurrentPosition = mBannerView.getCurrentItem();
            mCurrentPosition++;
            mBannerView.setCurrentItem(mCurrentPosition);
            mHandler.postDelayed(this, mDelayTime);
        }
    };

    private void addIndicator() {
        if (mIndicatorLayout == null) {
            return;
        }
        mIndicatorLayout.removeAllViews();
        for (int i = 0; i < mBannerList.size() - 2; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.shape_banner_indicator_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            mIndicatorLayout.addView(imageView, params);
        }
    }

    public List<T> getBannerData() {
        return mBannerList;
    }
}
