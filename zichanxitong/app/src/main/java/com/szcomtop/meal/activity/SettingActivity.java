package com.szcomtop.meal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.views.HeadView;

/**
 * Created by wuming on 16/11/20.
 */
public class SettingActivity extends  BaseActivity implements View.OnClickListener {

    private TextView mSettingPower;
    private TextView mSettingIP;

    private void assignViews() {
        mSettingPower = (TextView) findViewById(R.id.setting_power);
        mSettingIP = (TextView) findViewById(R.id.setting_IP);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        assignViews();
        initView();
    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LEFTONLY;
    }

    @Override
    public void initCenterTv(TextView cTv) {
        cTv.setText("设置");
    }

    private void initView() {
        mSettingIP.setOnClickListener(this);
        mSettingPower.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.setting_IP:{
                Intent intent = new Intent(this, IPActivity.class);
                startActivity(intent);
            }




                break;

            case R.id.setting_power:

                Intent intent = new Intent(this, PowerActivity.class);
                startActivity(intent);

                break;

            default:
                break;

        }
    }
}
