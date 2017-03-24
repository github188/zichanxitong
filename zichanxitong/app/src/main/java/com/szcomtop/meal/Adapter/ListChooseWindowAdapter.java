package com.szcomtop.meal.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommonAdapter;
import com.szcomtop.meal.common.ViewHolder;
import com.szcomtop.meal.model.ComImgTextInfo;
import com.szcomtop.meal.utils.Tools;

import java.util.List;

/**
 * 列表选择的adapter
 * Created by WYZ on 2016-05-11.
 */
public class ListChooseWindowAdapter<T extends ComImgTextInfo> extends CommonAdapter<T> {


    public ListChooseWindowAdapter(Context context, List<T> datas) {
        super(context, datas, R.layout.charge_list_choose_window_item);
    }



    @Override
    public void convert(ViewHolder holder, T t) {

        holder.setText(R.id.charge_list_choose_item_text,t.getText());

    }
}
