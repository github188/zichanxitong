package com.szcomtop.meal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iteam.supernfc.UHFApplication;
import com.szcomtop.meal.Adapter.AssetCountAdapter;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommCallBack;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.ComImgTextInfo;
import com.szcomtop.meal.model.CommonOperateResult;
import com.szcomtop.meal.model.UserInfo;
import com.szcomtop.meal.net.RestApi;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.szcomtop.meal.utils.StringUtils;
import com.szcomtop.meal.utils.UnicodeUtil;
import com.szcomtop.meal.utils.WindowUtil;
import com.szcomtop.meal.views.HeadView;
import com.szcomtop.meal.views.ListChooseWindow;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.type;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_BORROW;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_DAMAGED;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_OUT;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_OUT2;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_REGISTE;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_RETURN;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_SCRAP;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_STOCK;
import static com.szcomtop.meal.activity.CommonOperateActivity.TYPE_TRANSFER;

/**
 * Created by wuming on 16/12/24.
 * 未经作者书面同意，不得转发，复制，修改
 * 联系：hb.wuming@qq.com
 */

public class OptionActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_BORROW = 1;


    private String mTitle;
    private LinearLayout mPandianNumArea;
    private EditText mPandianNumEt;
    private ImageView mPandianArrow;
    private ImageView mPandianRefresh;
    private TextView mCommonOperateBorrowerTv;
    private LinearLayout mOptionBorrowTypeArea;
    private TextView mOptionBorrowTypeTv;
    private TextView mOptionBorrowTypeEt;
    private ImageView mOptionBorrowTypeArrow;
    private TextView mOptionZcTv;
    private ListView mOptionListview;
    private List<AssetInfo> mSelectedInfos;

    private List<String> pandianNums = new ArrayList<>();

    private int mType;
    private String mBorrowId;
    private OperateCallBack operateCallBack;
    private ArrayList<String> mBorrowTypes =  new ArrayList<String>();
    private TextView mPandianNumTv;

    private void assignViews() {
        mPandianNumArea = (LinearLayout) findViewById(R.id.pandian_num_area);
        mPandianNumEt = (EditText) findViewById(R.id.pandian_num_et);
        mPandianNumTv = (TextView) findViewById(R.id.pandian_num_tv);
        mPandianArrow = (ImageView) findViewById(R.id.pandian_arrow);
        mPandianRefresh = (ImageView) findViewById(R.id.pandian_refresh);
        mCommonOperateBorrowerTv = (TextView) findViewById(R.id.common_operate_borrower_tv);
        mOptionBorrowTypeArea = (LinearLayout) findViewById(R.id.option_borrow_type_area);
        mOptionBorrowTypeTv = (TextView) findViewById(R.id.option_borrow_type_tv);
        mOptionBorrowTypeEt = (TextView) findViewById(R.id.option_borrow_type_et);
        mOptionBorrowTypeArrow = (ImageView) findViewById(R.id.option_borrow_type_arrow);
        mOptionZcTv = (TextView) findViewById(R.id.option_zc_tv);
        mOptionListview = (ListView) findViewById(R.id.option_listview);

        mPandianArrow.setOnClickListener(this);
        mPandianRefresh.setOnClickListener(this);
        mCommonOperateBorrowerTv.setOnClickListener(this);
        mOptionBorrowTypeArrow.setOnClickListener(this);
        mOptionZcTv.setOnClickListener(this);

    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LRIGHTBTN;
    }

    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText(mTitle);
    }

    @Override
    public void initRightBtn(Button rBtn) {

        rBtn.setText("提交");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        assignViews();

        Intent intent = getIntent();
        mSelectedInfos = (List<AssetInfo>) intent.getSerializableExtra("data");
        mType = intent.getIntExtra("type", 0);
        mTitle = intent.getStringExtra("title");

        mOptionListview.setAdapter(new AssetCountAdapter(this, mSelectedInfos));
        mOptionZcTv.setText(String.format("资产：共%s条数据", mSelectedInfos.size()));

        initView();
    }

    private void initView() {
        switch (mType) {

            case TYPE_REGISTE:

                mPandianNumArea.setVisibility(View.VISIBLE);
                mPandianNumTv.setText("学校: ");
                mPandianNumEt.setEnabled(false);
                mPandianNumEt.setTextColor(Color.BLACK);
                UserInfo userInfo = UHFApplication.getUserInfo();
                if (userInfo!= null){
                    List<UserInfo.School> rbac_school = userInfo.rbac_school;
                    if (rbac_school != null && rbac_school.size() >0){
                        String school_name = rbac_school.get(0).school_name;
                        mPandianNumEt.setText(school_name);
                    }
                }
                mPandianRefresh.setVisibility(View.INVISIBLE);


                break;

            case TYPE_OUT:

                break;
            case TYPE_OUT2:

                mCommonOperateBorrowerTv.setText("领用人：");
                mCommonOperateBorrowerTv.setVisibility(View.VISIBLE);

                break;

            case TYPE_TRANSFER: {
                mBorrowTypes.add("内部调拨");
                mBorrowTypes.add("外部调拨");
                mOptionBorrowTypeTv.setText("调拨类型：");
                mOptionBorrowTypeArea.setVisibility(View.VISIBLE);
                mOptionBorrowTypeEt.setText("内部调拨");
                mOptionBorrowTypeEt.setEnabled(false);

            }

                break;
            case TYPE_SCRAP:

                break;
            case TYPE_BORROW:

                mBorrowTypes = new ArrayList<>();
                mBorrowTypes.add("内部借用");
                mBorrowTypes.add("外部借用");
                mOptionBorrowTypeTv.setText("借用类型：");
                mCommonOperateBorrowerTv.setText("借用人：");
                mCommonOperateBorrowerTv.setVisibility(View.VISIBLE);
                mOptionBorrowTypeArea.setVisibility(View.VISIBLE);
                mOptionBorrowTypeEt.setText("内部借用");
                mOptionBorrowTypeEt.setEnabled(false);

                break;
            case TYPE_DAMAGED:

                break;
            case TYPE_RETURN:

                break;

            case TYPE_STOCK:


                mPandianNumArea.setVisibility(View.VISIBLE);
                getPandianHistory();
                getPandianNew();

                break;
            default:
                break;


        }
    }


    @Override
    public void OnRightBtnClick(View v) {

        if (check()) {


            List<String> ids = new ArrayList<>();

            String operateId = PreferencesUtils.getString(this, Consts.OPERATE_ID);
            for (AssetInfo selectedInfo : mSelectedInfos) {
                ids.add(selectedInfo.rfid_code);
            }


            if (operateCallBack == null) {

                operateCallBack = new OperateCallBack();
            }


            switch (mType) {

                case TYPE_REGISTE:

                    showProgress("处理中...");

                    String schoolId = "";
                    String schoolname = mPandianNumEt.getText().toString().trim();
                    List<UserInfo.School> rbac_school = UHFApplication.getUserInfo().rbac_school;
                    if (rbac_school != null){
                        for (UserInfo.School school : rbac_school) {
                            if (school.school_name.equals(schoolname)){
                                schoolId = school.school_id;
                            }
                        }
                    }

                    RestApi.assetRegiste(ids, operateId,schoolId, operateCallBack);

                    break;

                case TYPE_OUT:

                    showProgress("处理中...");

                    RestApi.assetOutStorage(ids, operateId, operateCallBack);

                    break;
                case TYPE_OUT2:

                    showProgress("处理中...");

                    RestApi.assetRecive(ids, operateId, mBorrowId, operateCallBack);
                    break;

                case TYPE_TRANSFER:

                    showProgress("处理中...");

                    String trim = mOptionBorrowTypeEt.getText().toString().trim();
                    RestApi.assetTransfer(ids, operateId,getBorrowTypeStr(trim) ,operateCallBack);

                    break;
                case TYPE_SCRAP:

                    showProgress("处理中...");

                    RestApi.assetScrap(ids, operateId, operateCallBack);

                    break;
                case TYPE_BORROW:


                    String type = mOptionBorrowTypeEt.getText().toString().trim();

                    RestApi.assetBorrow(ids, operateId, mBorrowId, type ,operateCallBack);

                    break;
                case TYPE_DAMAGED:
                    showProgress("处理中...");

                    RestApi.assetDamaged(ids, operateId, operateCallBack);

                    break;
                case TYPE_RETURN:

                    showProgress("处理中...");

                    RestApi.assetReturn(ids, operateId, operateCallBack);

                    break;

                case TYPE_STOCK:

                    showProgress("处理中...");

                    RestApi.assetInventory(ids, operateId, mPandianNumEt.getText().toString(),PreferencesUtils.getString(this, Consts.OFFICE_ID), operateCallBack);

                    break;
                default:
                    break;


            }


        }

    }


    public  String  getBorrowTypeStr(String s){

        String res = "";

        switch (s){

            case "内部调拨":

                res = "6001";
                break;
            case "外部调拨":
                res = "6002";

                break;
            case "内部借用":

                res = "10001";

                break;
            case "外部借用":
                res = "10002";

                break;

        }

        return  res ;

    }

    public boolean check() {

        if (mPandianNumArea.isShown()) {
            if (StringUtils.isNull(mPandianNumEt.getText().toString().trim())) {
                showToast("请填写盘点号");
                return false;
            }
        }

        if (mCommonOperateBorrowerTv.isShown()) {

            if (StringUtils.isNull(mBorrowId)) {

                if (mType == TYPE_BORROW) {
                    showToast("请选择借用人");
                    return false;
                } else {
                    showToast("请选择领用人");
                    return false;
                }


            }

        }

        if (mOptionBorrowTypeArea.isShown()) {

            if (StringUtils.isNull(mOptionBorrowTypeEt.getText().toString().trim())) {
                if (mType == TYPE_BORROW) {
                    showToast("请选择借用类型");
                    return false;
                } else {
                    showToast("请选择调拨类型");
                    return false;
                }

            }
        }

        return true;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pandian_arrow:

                if (mType == TYPE_REGISTE){

                    onPandianArrowClick2();


                }else {

                    onPandianArrowClick();
                }

                break;

            case R.id.pandian_refresh:

                onPandianRefresh();

                break;

            case R.id.common_operate_borrower_tv:

                onBorrowClick();

                break;
            case R.id.option_zc_tv:

                onZcClick();

                break;

            case R.id.option_borrow_type_arrow:

                onTypeArrowClick();
                break;

        }

    }

    private void onTypeArrowClick() {

        ListChooseWindow listChooseWindow = initListWindow(mBorrowTypes, mOptionBorrowTypeEt);
        listChooseWindow.setWindowsWidth(mOptionBorrowTypeEt.getMeasuredWidth());
        listChooseWindow.showDown(mOptionBorrowTypeEt);
    }

    private void onZcClick() {
        if (mOptionListview.getVisibility() == View.VISIBLE) {
            mOptionListview.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.ui_submenu);
            drawable.setBounds(0,0,26,44);
            mOptionZcTv.setCompoundDrawables(null,null, drawable,null);
        } else {
            mOptionListview.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.ui_submenu_down);
            drawable.setBounds(0,0,44,26);
            mOptionZcTv.setCompoundDrawables(null,null,drawable,null);

        }
    }


    private void onBorrowClick() {


        Intent intent = new Intent(this, SortActivity.class);
        startActivityForResult(intent, REQUEST_BORROW);
        return;

    }

    private void onPandianRefresh() {


        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mAnimation.setInterpolator(new LinearInterpolator());
        mPandianRefresh.startAnimation(mAnimation);

        getPandianNew();
    }

    private void onPandianArrowClick() {
        WindowUtil.hideKeybord(mPandianNumEt, this);

        ListChooseWindow listChooseWindow = initListWindow(pandianNums, mPandianNumEt);
        listChooseWindow.setWindowsWidth(mPandianNumEt.getMeasuredWidth());
        listChooseWindow.showDown(mPandianNumEt);
    }


    private void onPandianArrowClick2() {
        WindowUtil.hideKeybord(mPandianNumEt, this);

        ArrayList<String> strings = new ArrayList<>();
        UserInfo userInfo = UHFApplication.getUserInfo();
        if (userInfo != null){
            List<UserInfo.School> rbac_school = userInfo.rbac_school;
            if (rbac_school != null){

                for (UserInfo.School school : rbac_school) {
                    strings.add(school.school_name);
                }
            }
        }
        ListChooseWindow listChooseWindow = initListWindow(strings, mPandianNumEt);
        listChooseWindow.setWindowsWidth(mPandianNumEt.getMeasuredWidth());
        listChooseWindow.showDown(mPandianNumEt);
    }

    public void getPandianHistory() {

        RestApi.getPandianHistory(PreferencesUtils.getString(this, Consts.OFFICE_ID), new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                String s = UnicodeUtil.decodeUnicode(response);
                Log.i("wuming", "getPandianHistory:" + s);


            }
        });


    }


    public void getPandianNew() {

        RestApi.getPandianNew("", new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                mPandianRefresh.clearAnimation();

                mPandianRefresh.setRotation(0);
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                mPandianRefresh.clearAnimation();
                mPandianRefresh.setRotation(0);


                String s = UnicodeUtil.decodeUnicode(response);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int state = (int) jsonObject.get("state");
                    if (state == 1) {
                        String pandianNum = (String) jsonObject.get("data");
                        pandianNums.add(pandianNum);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    private ListChooseWindow initListWindow(List<String> datas, final TextView et) {
        final ArrayList<ComImgTextInfo> infoList = new ArrayList<>();


        for (String pandianNum : datas) {
            ComImgTextInfo comImgTextInfo = new ComImgTextInfo();
            comImgTextInfo.setContentText(pandianNum);
            infoList.add(comImgTextInfo);
        }

        ListChooseWindow mListWindow = new ListChooseWindow(this, infoList).createWindow();
        mListWindow.setBackgroundRes(R.drawable.light_grey_stroke_slide_bg);
        mListWindow.setCallBack(new CommCallBack() {
            @Override
            public void callBack(Object... object) {
                Integer position = (Integer) object[0];

                String text = infoList.get(position).getText();
                et.setText(text);
                if (et instanceof  EditText){

                    ((EditText) et).setSelection(text.length());
                }
            }

            @Override
            public void progress(String str1, String str2) {

            }
        });

        return mListWindow;


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_BORROW) {
                if (data != null) {
                    String name = data.getStringExtra("name");
                    mBorrowId = data.getStringExtra("id");
                    if (mType == TYPE_BORROW) {

                        mCommonOperateBorrowerTv.setText("借用人：" + name);

                    } else if (mType == TYPE_OUT2) {

                        mCommonOperateBorrowerTv.setText("领用人：" + name);

                    }

                }

            }

        }


    }




    public class OperateCallBack extends Callback<String> {

        private List<AssetInfo> succeedData = new ArrayList<>();
        private List<AssetInfo> failedData = new ArrayList<>();



        @Override
        public String parseNetworkResponse(Response response) throws Exception {

            dismissProgress();
            Request request = response.request();
            return response.body().string();
        }

        @Override
        public void onError(Call call, Exception e) {

            dismissProgress();

            showToast("网络出错，请重试！");
            Log.i("wuming", e.getMessage());

        }

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                return;
            }
            String s = UnicodeUtil.decodeUnicode(response);

            Log.i("wuming", "onResponse====>" + s);

            //TODO 数据多时要开线程
            CommonOperateResult commonOperateResult = new Gson().fromJson(s, CommonOperateResult.class);
            if (commonOperateResult.state == 1) {
                final List<AssetInfo> data = commonOperateResult.data;
                if (data != null) {
                    succeedData.clear();
                    failedData.clear();
                    for (AssetInfo item : data) {

                        if ("1".equals(item.status)) {

                            succeedData.add(item);

                        } else {
                            failedData.add(item);
                        }

                    }

                    if (failedData.size() == 0) {

                        showToast("全部提交成功");
                        finish();

                    } else {

                        //TODO  数据库读取数据拼

                        final AssetInfoDao assetInfoDao = new AssetInfoDao(OptionActivity.this);


                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                assetInfoDao.refreshList(data);
                                mOptionListview.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(OptionActivity.this, ResultActivity.class);
                                        intent.putExtra("succeed", (Serializable) succeedData);
                                        intent.putExtra("fail", (Serializable) failedData);
                                        startActivity(intent);
                                    }
                                });

                            }
                        }).start();


                    }


                }


            } else {
                showToast("网络出错，请重试！");
            }

            Log.i("wuming", s);

        }
    }


}
