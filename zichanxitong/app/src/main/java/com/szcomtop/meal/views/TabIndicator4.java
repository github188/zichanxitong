package com.szcomtop.meal.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szcomtop.meal.R;

/**
 * Created by wuming on 16/5/20.
 */
public class TabIndicator4 extends RelativeLayout implements View.OnClickListener {


    private TextView tab1;
    private TextView tab0;
    private int seletedTab;
    private int mSelectedTab  = -1;
    private OnSelectedListenner mSelectedListenner;
    private TextView tab2;

    public TabIndicator4(Context context) {
        super(context);
        initView();
    }



    public TabIndicator4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TabIndicator4(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {

        View view = View.inflate(getContext(), R.layout.charge_tab_indicator4 , null);
        tab0 = (TextView) view.findViewById(R.id.charge_tab_indicator_tv_1);
        tab1 = (TextView) view.findViewById(R.id.charge_tab_indicator_tv_2);
        tab2 = (TextView) view.findViewById(R.id.charge_tab_indicator_tv_3);
        tab0.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);



        this.addView(view,layoutParams);

        setSeletedTab(0);


    }


    public void setSeletedTab(int seletedTab) {

        if (mSelectedTab == seletedTab){
            return;
        }

        switch (seletedTab){

            case 0:
                mSelectedTab = seletedTab ;
                resetTab();
                tab0.setSelected(true);
                if (mSelectedListenner != null){
                    mSelectedListenner.onSelected(0);
                }
                break;

            case  1:
                mSelectedTab = seletedTab ;
                resetTab();
                tab1.setSelected(true);
                if (mSelectedListenner != null){
                    mSelectedListenner.onSelected(1);
                }
                break;
            case  2:
                mSelectedTab = seletedTab ;
                resetTab();
                tab2.setSelected(true);
                if (mSelectedListenner != null){
                    mSelectedListenner.onSelected(2);
                }
                break;
            default:

                break;
        }

    }


    public void resetTab(){
        tab0.setSelected(false);
        tab1.setSelected(false);
        tab2.setSelected(false);
    }


    public  void setOnselectedListenner(OnSelectedListenner l){

        mSelectedListenner = l ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.charge_tab_indicator_tv_1:
                setSeletedTab(0);
                break;
            case R.id.charge_tab_indicator_tv_2:
                setSeletedTab(1);
                break;
            case R.id.charge_tab_indicator_tv_3:
                setSeletedTab(2);
                break;
            default:
                break;
        }
    }


    public interface   OnSelectedListenner {

       void  onSelected(int index);

    }


}
