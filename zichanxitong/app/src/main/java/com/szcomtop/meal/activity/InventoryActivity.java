package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.szcomtop.meal.Adapter.MyExpandableListAdapter;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.net.RestApi;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class InventoryActivity extends BaseActivity  implements View.OnClickListener{
    private EditText search_processing;//搜索框
    private ImageView search_back; //返回按钮
    private ImageView search_cancel; //取消搜索
    private TextView search_btn;//搜索按钮
    private String TAG = getClass().getName();
    private ExpandableListView list_result;
    private List<AssetInfo> data = new ArrayList<>();
    public MyExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        search_processing = (EditText) findViewById(R.id.search_processing);
        search_back  = (ImageView) findViewById(R.id.search_back);
        search_back.setOnClickListener(this);
        search_cancel  = (ImageView) findViewById(R.id.search_cancel);
        search_cancel.setOnClickListener(this);
        search_btn = (TextView) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        list_result = (ExpandableListView) findViewById(R.id.list_result);
//        list_result.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //盘点任务
        if(PreferencesUtils.getString(this,"account_id")!=null){
            String id = PreferencesUtils.getString(this,"account_id");
            getInventoryTask(id);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_back:
                finish();
                break;
            case R.id.search_cancel:
                search_processing.setText("");
                break;
            case R.id.search_btn:
                if(search_processing.getText().toString().trim().equals("")){
                    return;
                }else {
                    List<AssetInfo> resultList = new ArrayList<>();
                    AssetInfoDao assetInfoDao = new AssetInfoDao(InventoryActivity.this);
                    resultList = assetInfoDao.queryStoresByName(""+search_processing.getText().toString().trim(),0);
                    Log.d(TAG,resultList+"");
                    Log.e(TAG,"搜索结果条数:"+resultList.size());
                    data = resultList;
                    adapter = new MyExpandableListAdapter(this,data);
                    list_result.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    /**
    * 获取盘点任务
    */
    public void getInventoryTask(String id) {

        showProgress("正在登陆");
        RestApi.getInventorytask(id, ""+1, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
//                Log.d("RENRENREN","返回 错误信息 error = "+call.request().body().toString());
                e.printStackTrace();
                dismissProgress();
                showToast("网络出错，请检查。");
            }
            @Override
            public void onResponse(String response) {
                dismissProgress();
                Log.d("RENRENREN","返回response"+response);
            }
        });
    }
}
