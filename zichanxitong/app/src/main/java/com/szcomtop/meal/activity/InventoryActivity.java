package com.szcomtop.meal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iteam.supernfc.bean.InventoryInfo;
import com.szcomtop.meal.Adapter.InventoryListAdapter;
import com.szcomtop.meal.Adapter.MyExpandableListAdapter;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.handler.Handler_Json;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.net.RestApi;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.zhy.http.okhttp.builder.HasParamsable;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class InventoryActivity extends BaseActivity  implements View.OnClickListener{
    private ImageView search_back; //返回按钮
    private String TAG = getClass().getName();
    private ExpandableListView list_result;
    private List<AssetInfo> data = new ArrayList<>();
    public InventoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        search_back  = (ImageView) findViewById(R.id.search_back);
        search_back.setOnClickListener(this);

        list_result = (ExpandableListView) findViewById(R.id.list_result);
        list_result.setAdapter(adapter);
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
            default:
                break;
        }
    }

    /**
    * 获取盘点任务
    */
    public void getInventoryTask(String id) {

        showProgress("加载中。。。");
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
//                Handler_Json.JsonToCollection(response);
//                Log.d("RENRENREN","返回response"+response);
                JSONObject json = null;
                JSONArray json1 = null;
                try {
                    json = new JSONObject(response);
                    json1 = json.getJSONArray("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<InventoryInfo> data = new Gson().fromJson(json1.toString(), new TypeToken<List<InventoryInfo>>() {}.getType());
                if(data!=null&&data.size()>0){
                    adapter = new InventoryListAdapter(InventoryActivity.this,data);
                    list_result.setAdapter(adapter);
                    list_result.expandGroup(0);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(InventoryActivity.this,"当前用户无盘点任务！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
