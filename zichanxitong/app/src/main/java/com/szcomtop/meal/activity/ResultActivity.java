package com.szcomtop.meal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.szcomtop.meal.Adapter.AssetCountAdapter;
import com.szcomtop.meal.R;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.CommonOperateResult;
import com.szcomtop.meal.views.HeadView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuming on 16/11/30.
 * 未经作者书面同意，不得转发，复制，修改
 * 联系：hb.wuming@qq.com
 */
public class ResultActivity  extends   BaseActivity {


    private List<AssetInfo> succeedData;
    private List<AssetInfo> failData;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);



        listView = (ListView) findViewById(R.id.result_listview);

        Intent intent = getIntent();
       succeedData  = (List<AssetInfo>) intent.getSerializableExtra("succeed");
        failData  = (List<AssetInfo>) intent.getSerializableExtra("fail");

        AssetCountAdapter assetCountAdapter = new AssetCountAdapter(this, failData);
        assetCountAdapter.setShowCheckbox(false);
        listView.setAdapter(assetCountAdapter);

    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LEFTONLY;
    }

    @Override
    public void initCenterTv(TextView cTv) {
        cTv.setText("结果");
    }
}
