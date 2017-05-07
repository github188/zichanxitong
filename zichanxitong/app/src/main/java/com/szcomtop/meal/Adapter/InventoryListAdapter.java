package com.szcomtop.meal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.iteam.supernfc.bean.InventoryInfo;
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
public class InventoryListAdapter extends BaseExpandableListAdapter {
    private List<InventoryInfo> data = new ArrayList<>();
    private Context mContext;

    public InventoryListAdapter(Context context, List<InventoryInfo> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.inventory_listview_group, null);
            holder.inventory_no = (TextView)view.findViewById(R.id.inventory_no);
            holder.inventory_tv = (TextView)view.findViewById(R.id.inventory_tv);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }
        holder.inventory_no.setText("盘点编号:"+data.get(groupPosition).getInventory_no()+"");
        holder.inventory_tv.setOnClickListener(new View.OnClickListener() {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.inventory_listview_child, null);
            holder.name = (TextView)view.findViewById(R.id.name);
            holder.category_id = (TextView)view.findViewById(R.id.category_id);
            holder.ID_ = (TextView)view.findViewById(R.id.ID_);
            holder.workarea_id = (TextView)view.findViewById(R.id.workarea_id);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }

        holder.name.setText("资产名："+data.get(groupPosition).getName()+"");
        holder.category_id.setText("类型："+data.get(groupPosition).getCategory_id()+"");
        holder.ID_.setText("ID："+data.get(groupPosition).getID_()+"");
        holder.workarea_id.setText("工号："+data.get(groupPosition).getWorkarea_id()+"");
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        public TextView inventory_no;
        public TextView inventory_tv;
    }

    class ChildHolder{
        public TextView name;
        public TextView category_id;
        public TextView ID_;
        public TextView workarea_id;
    }
}
