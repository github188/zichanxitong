package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.szcomtop.meal.views.HeadView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by wuming on 16/11/20.
 */
public class PowerActivity extends  BaseActivity implements DiscreteSeekBar.OnProgressChangeListener {


    private TextView mPowerTv;
    private DiscreteSeekBar mPowerSeekbar;
    private int mValue;

    private void assignViews() {
        mPowerTv = (TextView) findViewById(R.id.power_tv);
        mPowerSeekbar = (DiscreteSeekBar) findViewById(R.id.power_seekbar);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        assignViews();
        initView();
    }

    private void initView() {
        mPowerSeekbar.setOnProgressChangeListener(this);

        int dBM = PreferencesUtils.getInt(this, Consts.D_BM, 33);
        mValue = dBM ;
        mPowerTv.setText("当前功率："+dBM+ Consts.D_BM);
        mPowerSeekbar.setProgress(dBM);

    }

    @Override
    public void OnRightBtnClick(View v) {

        PreferencesUtils.putInt(this, Consts.D_BM,mValue);
        showToast("设置成功");
        finish();

    }

    @Override
    public int getHeaderType() {
        return  HeadView.TYPE_LRIGHTBTN;
    }

    @Override
    public void initRightBtn(Button rBtn) {

        rBtn.setText("确定");
    }

    @Override
    public void initCenterTv(TextView cTv) {
        cTv.setText("功率设置");
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

        mPowerTv.setText("当前功率："+value+ Consts.D_BM);
        mValue = value ;

    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }
}
