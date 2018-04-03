package com.promiseland.ustory.ui.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.promiseland.ustory.R;
import com.promiseland.ustory.ui.widget.custom.HorizontalScrollViewEx;
import com.promiseland.ustory.ui.widget.custom.LeafLoadingView;
import com.promiseland.ustory.ui.widget.custom.ListViewEx;
import com.promiseland.ustory.ui.widget.custom.GooView;
import com.promiseland.ustory.ui.widget.custom.MovingTextView;
import com.promiseland.ustory.ui.widget.custom.PieView;
import com.promiseland.ustory.ui.widget.custom.RadarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends Activity {
    @BindView(R.id.movingTextView)
    MovingTextView movingView;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollViewEx mHorizontalScrollViewEx;
    @BindView(R.id.leafLoading)
    LeafLoadingView mLeafLoadingView;
    @BindView(R.id.pieView)
    PieView mPieView;
    @BindView(R.id.radarView)
    RadarView mRadarView;
    @BindView(R.id.magicCircle)
    GooView mMagicCircle;

    static List<String> sDatas;

    static {
        sDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            sDatas.add("name " + i);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

//        initView();
//        showLoading();
//        showPieView();
//        showRadarView();
        showMagicCircle();
    }

    private void showMagicCircle() {

    }

    private void showRadarView() {

    }

    private void showPieView() {
        List<PieView.PieData> mDatas = new ArrayList<>();
        Random random = new Random(20);
        for (int i = 0; i< 6; i++) {
            PieView.PieData data = new PieView.PieData("name " + i, random.nextInt(10) + 5);
            mDatas.add(data);
        }
        mPieView.setStartAngle(30);
        mPieView.setPieDatas(mDatas);
    }

    private void showLoading() {
        mLeafLoadingView.post(new Runnable() {
            @Override
            public void run() {
                int currentProgress = mLeafLoadingView.getCurrentProgress();
                if(currentProgress <= 100 ) {
                    mLeafLoadingView.setCurrentProgress(currentProgress + 5);
                    mLeafLoadingView.postDelayed(this, 500);
                }

            }
        });
    }

    private void initView() {
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < 3; i++) {
            View content = layoutInflater.inflate(R.layout.layout_content, mHorizontalScrollViewEx, false);

            TextView title = content.findViewById(R.id.title);
            title.setText("title " + i);
            ListViewEx list = content.findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sDatas));
            list.setHorizontalScrollViewEx(mHorizontalScrollViewEx);

            mHorizontalScrollViewEx.addView(content);
        }
    }

    @OnClick(R.id.movingTextView)
    void move() {
        movingView.smoothScrollTo(100, 100);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
        return super.dispatchTouchEvent(ev);

    }
}
