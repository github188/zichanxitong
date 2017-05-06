package com.szcomtop.meal.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommonDialog;
import com.szcomtop.meal.views.HeadView;

/**
 * Created by wuming on 16/3/23.
 */
public class BaseActivity extends FragmentActivity implements HeadView.OnHeadInitListener, HeadView.OnHeadClickListener {

    private CommonDialog mProgressDialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }


    @Override
    protected void onResume() {
        super.onResume();
        initHeader();
    }



    public  void showToast(String text){

        Toast.makeText(this,text,Toast.LENGTH_LONG).show();

    }



    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    public  void showProgress(String text){


        if (mProgressDialog == null){
            mProgressDialog = new CommonDialog(this);
            mProgressDialog.setCancelable(true);
        }

        mProgressDialog.setText(text);
        mProgressDialog.show();

    }


    public  void dismissProgress(){

        if (mProgressDialog != null && mProgressDialog.isShowing()){

            mProgressDialog.dismiss();
        }

    }


    private void initHeader() {

        HeadView headView = (HeadView) findViewById(R.id.title);
        if (headView != null){
            headView.setHeadParams(getHeaderType(),this);
            headView.setOnHeadClick(this);
        }

    }

    public int getHeaderType(){
        return  HeadView.TYPE_CENTERONLY ;

    }

    @Override
    public void initCenterTv(TextView cTv) {

    }

    @Override
    public void initLeftBtn(ImageButton lBtn) {

    }

    @Override
    public void initRightBtn(Button rBtn) {


    }

    @Override
    public void initRightIBtn(ImageButton ibtn) {

    }


    @Override
    public void OnCenterTVClick(View v) {

    }

    @Override
    public void OnLeftBtnClick(View v) {

        finish();

    }

    @Override
    public void OnRightBtnClick(View v) {

    }

    @Override
    public void OnRightIBtnClick(View v) {

    }
}
