package com.szcomtop.meal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommonConfirmDialog;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.AssetListResult;
import com.szcomtop.meal.net.RestApi;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.szcomtop.meal.utils.UnicodeUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by wuming on 16/11/15.
 */
public class MainActivity  extends  BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        findViewById(R.id.main_data).setOnClickListener(this);
        findViewById(R.id.main_device).setOnClickListener(this);
        findViewById(R.id.main_setting).setOnClickListener(this);
    }

    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText("功能选择");
    }


    @Override
    public void onBackPressed() {

        CommonConfirmDialog dialog = new CommonConfirmDialog(this, "请确定", "确定要退出吗？", "取消", "确定");
        dialog.setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()){

            case R.id.main_data:

                showProgress("同步中");
//                getDeleteAssetData();
//                getAssetData();
                syncAssetData();

                break;

            case R.id.main_device:

            intent.setClass(this,OperateActivity.class);
                startActivity(intent);

                break;

            case R.id.main_setting:

                intent.setClass(this,SettingActivity.class);
                startActivity(intent);

                break;
            default:
                break;


        }

    }




    private void getDeleteAssetData() {

        String update_time = PreferencesUtils.getString(this, Consts.UPDATE_TIME, "1000000000");

        RestApi.getAllDeleteAsset(update_time, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                dismissProgress();
                showToast("同步数据失败");
                Log.i("wuming", e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                dismissProgress();

                String s = UnicodeUtil.decodeUnicode(response);
                Log.i("wuming", s);
                AssetListResult assetListResult = new Gson().fromJson(s, AssetListResult.class);
                if (assetListResult.state == 1) {

                    showToast("同步数据成功");

                    List<AssetInfo> data = assetListResult.data;
                    if (data != null) {
                        AssetInfoDao assetInfoDao = new AssetInfoDao(MainActivity.this);
                        assetInfoDao.deleteList(data);

                    }


                }

            }
        });

    }

    /**
    * 同步资产数据
    */
    private void syncAssetData(){
        String officeId = PreferencesUtils.getString(this,"office_id");
        RestApi.syncAssetData(officeId, "1", new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("RENRENREN","返回 onError="+call.request().body());
                dismissProgress();
            }

            @Override
            public void onResponse(String response) {
                Log.v("RENRENREN","返回 response="+response.toString());
                dismissProgress();
            }
        });
    }


    private void getAssetData() {

        String update_time = PreferencesUtils.getString(this, Consts.UPDATE_TIME, "-1");

        RestApi.updateAssetData(update_time, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                dismissProgress();
//                showToast("同步数据失败");
                Log.i("wuming", e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                dismissProgress();

                String s = UnicodeUtil.decodeUnicode(response);
                Log.i("wuming", s);
                AssetListResult assetListResult = new Gson().fromJson(s, AssetListResult.class);
                if (assetListResult.state == 1) {

//                    showToast("同步数据成功");

                    List<AssetInfo> data = assetListResult.data;
                    if (data != null) {
                        AssetInfoDao assetInfoDao = new AssetInfoDao(MainActivity.this);
                        assetInfoDao.updateList(data);

                    }

                    getDeleteAssetData();

                }

            }
        });

    }


}
