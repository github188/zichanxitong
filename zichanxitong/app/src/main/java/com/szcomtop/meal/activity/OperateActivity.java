package com.szcomtop.meal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommonAdapter;
import com.szcomtop.meal.common.ViewHolder;
import com.szcomtop.meal.views.HeadView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuming on 16/11/15.
 */
public class OperateActivity extends  BaseActivity implements AdapterView.OnItemClickListener {


    //private ListView listView;
    private List<String> strings;
    private GridView gridView;

    private int[] resIds = new int[]{R.drawable.xinzeng_icon,R.drawable.chuku_icon,R.drawable.lingyong_icon
    ,R.drawable.diaobo_icon,R.drawable.baofei_icon,R.drawable.jieyong_icon,R.drawable.baosun_icon,R.drawable.tuihuan_icon,R.drawable.pandian_icon,R.drawable.find_icon,R.drawable.my_task_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);
//        listView = (ListView) findViewById(R.id.operate_listview);
        gridView = (GridView) findViewById(R.id.operate_gridview);
        String[] stringArray = getResources().getStringArray(R.array.operate_list);
        strings = Arrays.asList(stringArray);
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, strings, R.layout.operate_girdview_item) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.operate_list_item_tv,s);

                holder.setImageResource(R.id.operate_list_item_iv,resIds[holder.getPosition()]);


            }
        };
        gridView.setAdapter(commonAdapter);
        gridView.setOnItemClickListener(this);


    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LEFTONLY;
    }

    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText("设备操作");
    }

    @Override
    public void OnLeftBtnClick(View v) {
        super.OnLeftBtnClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(strings.get(position).equals("查找")){
            //查找逻辑
            startActivity(new Intent(this,FindActivity.class));
        }else {
            Intent intent = new Intent(this, CommonOperateActivity.class);
            intent.putExtra("title",strings.get(position));
            intent.putExtra("type",position+1);
            startActivity(intent);
        }

    }
}
