package com.szcomtop.meal.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.utils.DateUtil;
import com.szcomtop.meal.utils.FileUtils;
import com.szcomtop.meal.utils.StringUtils;
import com.szcomtop.meal.verification.Rule;
import com.szcomtop.meal.verification.Validator;
import com.szcomtop.meal.verification.annotation.Required;
import com.szcomtop.meal.verification.annotation.TextRule;
import com.szcomtop.meal.views.HeadView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class AddAssetsActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private String mTitle;
    private String json;
    private Button select_img;
    Validator validator = null;
    AssetInfoDao dao = null;
//    ed_name ed_price ed_year_valid  ed_data
//    spinner_brand spinner_type spinner_model

    @Required(message = "请填写资产名", order = 1)
    private EditText ed_name;
    @Required(message = "请填写价格", order = 2)
    private EditText ed_price;
    @Required(message = "请填写使用年限", order = 4)
    private EditText ed_year_valid;
    @Required(message = "请填写购买时间", order = 3)
    private EditText ed_data;

    //品牌
    @Required(message = "请选择品牌", order = 5)
    Spinner spinner_brand;
    //类型
    @Required(message = "请选择类型", order = 6)
    Spinner spinner_type;
    //信号
    @Required(message = "请选择型号", order = 7)
    Spinner spinner_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets);
        mTitle = getIntent().getStringExtra("title");
        json = getIntent().getStringExtra("json");
        initView();
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

        rBtn.setText("保存");
        rBtn.setOnClickListener(this);
    }


    private void initView() {
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_price = (EditText) findViewById(R.id.ed_price);
        ed_year_valid = (EditText) findViewById(R.id.ed_year_valid);
        ed_data = (EditText) findViewById(R.id.ed_data);
        spinner_brand = (Spinner) findViewById(R.id.spinner_brand);
        spinner_type = (Spinner) findViewById(R.id.spinner_type);
        spinner_model = (Spinner) findViewById(R.id.spinner_model);

//        select_img = (Button) findViewById(R.id.select_img);
//        select_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        try {
            dao = new AssetInfoDao(this);
            AssetInfo assetInfo =new Gson().fromJson(json,AssetInfo.class);
            assetInfo.setBatch_no(DateUtil.date2str(DateUtil.JAVA_TIME_FORAMTER_3, new Date())+ StringUtils.getWordName());
            assetInfo.setBuy_date(ed_data.getText().toString());
            assetInfo.setPrice(ed_price.getText().toString());
            assetInfo.setDurable_years(ed_year_valid.getText().toString());
            assetInfo.setName(ed_name.getText().toString());
            assetInfo.setCreate_date(DateUtil.date2str(DateUtil.JAVA_TIME_FORAMTER_2, new Date()));
            assetInfo.setKeeper_id("1");
            assetInfo.setModel_id(spinner_model.getSelectedItemId() + "");
            assetInfo.setBrand_id(spinner_brand.getSelectedItemId() + "");
            assetInfo.setCategory_id(spinner_type.getSelectedItemId() + "");
            assetInfo.setState("0");
            assetInfo.setWorkarea_id("1");
            Log.i("AddAssetsActivity", spinner_brand.getSelectedItemId() + "");
            Log.i("AddAssetsActivity", ed_name.getText().toString());
            dao.add(assetInfo);
            showToast("保存成功");
            Intent data = new Intent();
            data.putExtra("assetInfo",new Gson().toJson(assetInfo));
            this.setResult(RESULT_OK,data);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        showToast(failedRule.getFailureMessage());
    }
}