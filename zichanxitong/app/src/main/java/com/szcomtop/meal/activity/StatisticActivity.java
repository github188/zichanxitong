package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.views.HeadView;
import com.szcomtop.meal.views.TabIndicator4;

/**
 * Created by wuming on 16/11/20.
 */
public class StatisticActivity  extends BaseActivity {


    private TabIndicator4 mStatisticIndicator;
    private ViewPager mStatisticViewpager;

    private void assignViews() {
        mStatisticIndicator = (TabIndicator4) findViewById(R.id.statistic_indicator);
        mStatisticViewpager = (ViewPager) findViewById(R.id.statistic_viewpager);
    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LEFTONLY;
    }

    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText("统计");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        assignViews();
        initView();


    }




    private void initView() {

    }
}
