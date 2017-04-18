package com.szcomtop.meal.activity;

import android.content.DialogInterface;
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
import com.szcomtop.meal.model.AssetInfo;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends BaseActivity  implements View.OnClickListener{
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
                    AssetInfoDao assetInfoDao = new AssetInfoDao(FindActivity.this);
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
}
