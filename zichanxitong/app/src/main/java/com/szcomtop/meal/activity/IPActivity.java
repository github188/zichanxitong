package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szcomtop.meal.R;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.szcomtop.meal.utils.StringUtils;
import com.szcomtop.meal.views.HeadView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuming on 16/11/20.
 */
public class IPActivity extends  BaseActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        editText = (EditText) findViewById(R.id.ip_et);
        String ip = PreferencesUtils.getString(this, Consts.SERVER_IP, "");
        editText.setText(ip);
        editText.setSelection(ip.length());

    }


    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LRIGHTBTN;
    }

    @Override
    public void initRightBtn(Button rBtn) {
        rBtn.setText("确定");
    }

    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText("IP设置");
    }


    @Override
    public void OnRightBtnClick(View v) {

     //   String ipAndPort="((1?\\d{1,2}|2[1-5][1-5])\\.){3}(1?\\d{1,2}|2[1-5][1-5]):([1-5]?\\d{1,4}|6[1-5][1-5][1-3][1-5])";

//        String ipAndPort = "\\d{2,3}([.]\\d{1,3}){3}:\\d{2,5}";
    //    Pattern ipAndPortPattern=Pattern.compile(ipAndPort);
        String str = editText.getText().toString();


   //     Matcher matcher = ipAndPortPattern.matcher(str);

        if (!StringUtils.isNull(str)){

            PreferencesUtils.putString(this,Consts.SERVER_IP,str);
            showToast("设置成功");
            finish();

        }else{
            Toast.makeText(this,"请输入",0).show();
        }


    }
}
