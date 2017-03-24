package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.util.Log;

import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.model.AssetInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by wuming on 16/11/13.
 */
public class TestActivity   extends   BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetInfo assetInfo = new AssetInfo("3412414", "笔记本", "bfahjkfa","");

        AssetInfoDao assetInfoDao = new AssetInfoDao(this);
        assetInfoDao.add(assetInfo);


        List<AssetInfo> assetInfos = assetInfoDao.queryAll();
        if (assetInfos!= null){
            for (AssetInfo info : assetInfos) {
                Log.i("wuming",info.toString());
            }

        }


        OkHttpUtils.get().url("http://221.131.145.100:8181/asset_interface/f/service/getUserName.json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.i("wuming",response);



            }
        });



    }
}
