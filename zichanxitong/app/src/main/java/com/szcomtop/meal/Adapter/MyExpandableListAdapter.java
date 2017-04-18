package com.szcomtop.meal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.model.AssetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rjh
 * @Description:
 * @version:${MODULE_VERSION}
 * @FileName:MyExpandableListAdapter
 * @Package com.szcomtop.meal.Adapter
 * @Date 2017/4/18 16:25
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private List<AssetInfo> data = new ArrayList<>();
    private Context mContext;

    public MyExpandableListAdapter(Context context, List<AssetInfo> list){
        mContext = context;
        this.data = list;
    }
    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if(view == null){
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.find_listview_group, null);
            holder.RFID_CODE = (TextView)view.findViewById(R.id.RFID_CODE);
            holder.find_tv = (TextView)view.findViewById(R.id.find_tv);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }
        holder.RFID_CODE.setText(data.get(groupPosition).getRfid_code()+"");
        holder.find_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //资产查找逻辑
            }
        });
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if(view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.find_listview_child, null);
            holder.name = (TextView)view.findViewById(R.id.name);
            holder.price = (TextView)view.findViewById(R.id.price);
            holder.create_date = (TextView)view.findViewById(R.id.create_date);
            holder.buy_date = (TextView)view.findViewById(R.id.buy_date);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }

        holder.name.setText(data.get(groupPosition).getName()+"");
        holder.price.setText(data.get(groupPosition).getPrice()+"");
        holder.create_date.setText(data.get(groupPosition).getCreate_date()+"");
        holder.buy_date.setText(data.get(groupPosition).getBuy_date()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        public TextView RFID_CODE;
        public TextView find_tv;
    }

    class ChildHolder{
        public TextView name;
        public TextView price;
        public TextView create_date;
        public TextView buy_date;
    }
}
