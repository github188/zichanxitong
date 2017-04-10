package com.szcomtop.meal.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.iteam.supernfc.bean.IFCategory;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommonAdapter;
import com.szcomtop.meal.common.ViewHolder;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuming on 16/11/16.
 */
public class AssetCountAdapter extends CommonAdapter<AssetInfo> implements View.OnClickListener {


    private boolean mShowCheckbox;
    private boolean mIsSelectAll;

    public AssetCountAdapter(Context context, List datas) {
        super(context, datas, R.layout.count_asset_listview_item);
    }


    @Override
    public void convert(ViewHolder holder, AssetInfo assetInfo) {

        holder.setText(R.id.count_list_item_id,"标签ID:  "+assetInfo.rfid_code)
                .setText(R.id.count_list_item_type,"设备类型:  "+  (StringUtils.isNull(assetInfo.name) ? "未录入" :assetInfo.name))
                .setText(R.id.count_list_item_name,"设备名称:  "+(StringUtils.isNull(assetInfo.name) ? "未录入" :assetInfo.name));
        View view = holder.getView(R.id.ist_group_item_iv);
        TextView error = holder.getView(R.id.count_list_item_error);
        if ("1".equals(assetInfo.status)){

            error.setVisibility(View.GONE);

        }else {
            error.setVisibility(View.VISIBLE);
            if (!StringUtils.isNull(assetInfo.msg)){
                error.setText(assetInfo.msg);

            }


        }
        if (mShowCheckbox){
            view.setVisibility(View.VISIBLE);
         //   view.setOnClickListener(this);
            view.setTag(assetInfo);
            view.setSelected(assetInfo.isSelected);
        }else {

            view.setVisibility(View.GONE);


        }


    }

    public void changeSelect(int position){

        if (mDatas != null){

            AssetInfo assetInfo = mDatas.get(position);
            assetInfo.isSelected = !assetInfo.isSelected  ;
            notifyDataSetChanged();

        }

    }

    @Override
    public void onClick(View v) {

        AssetInfo tag = (AssetInfo) v.getTag();
        tag.isSelected = !tag.isSelected ;
        notifyDataSetChanged();

    }


    public void setShowCheckbox(boolean isShow){

        mShowCheckbox = isShow ;
        notifyDataSetChanged();

    }


    public void  selectAll(boolean select){


        if (mDatas != null){


            for (AssetInfo info : mDatas) {

                info.isSelected =select ;

            }

            mIsSelectAll = select ;
            notifyDataSetChanged();

        }
    }


    public  boolean isSelectAll(){

        return  mIsSelectAll ;
    }


    public  List<AssetInfo>  getSelectedInfos(){

        if (mDatas != null){

            ArrayList<AssetInfo> assetInfos = new ArrayList<>();

            for (AssetInfo info : mDatas) {
                if (info.isSelected){
                    assetInfos.add(info);
                }

            }
            return  assetInfos ;

        }else {

            return  null ;
        }

    }

}
