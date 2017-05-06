package com.szcomtop.meal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.iteam.supernfc.UHFApplication;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.Dao.UserInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommCallBack;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.AssetListResult;
import com.szcomtop.meal.model.ComImgTextInfo;
import com.szcomtop.meal.model.UserInfo;
import com.szcomtop.meal.model.UserListResult;
import com.szcomtop.meal.net.Const;
import com.szcomtop.meal.net.RestApi;
import com.szcomtop.meal.utils.MD5Util;
import com.szcomtop.meal.utils.PreferencesUtils;
import com.szcomtop.meal.utils.UnicodeUtil;
import com.szcomtop.meal.utils.WindowUtil;
import com.szcomtop.meal.views.ListChooseWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.Call;

import static android.R.attr.id;

/**
 * Created by wuming on 16/11/20.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout mChargeLoginLayout;
    private LinearLayout mContent;
    private EditText mServer;
    private EditText mUserName;
    private ImageView mUiChangeUserName;
    private EditText mUserPassword;
    private Button mLogin;
    private ListChooseWindow mListWindow;

    private void assignViews() {
        mChargeLoginLayout = (RelativeLayout) findViewById(R.id.charge_login_layout);
        mContent = (LinearLayout) findViewById(R.id.content);
        mServer = (EditText) findViewById(R.id.server);
        mUserName = (EditText) findViewById(R.id.user_name);
        mUiChangeUserName = (ImageView) findViewById(R.id.ui_change_user_name);
        mUserPassword = (EditText) findViewById(R.id.user_password);
        mLogin = (Button) findViewById(R.id.login);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignViews();

        initView();

        initListWindow();


    }

    private void initView() {

        String string = PreferencesUtils.getString(this, Consts.SERVER_IP, Const.HOST_TEST.replace("http://", ""));
        mServer.setText(string);
        if(PreferencesUtils.getString(this,"login_name")!=null){
            mUserName.setText(""+PreferencesUtils.getString(this,"login_name"));
        }
        if(PreferencesUtils.getString(this,"password")!=null){
            mUserPassword.setText(""+PreferencesUtils.getString(this,"password"));
        }
        mServer.setSelection(string.length());
        mLogin.setOnClickListener(this);

    }

    private void initListWindow() {
        final ArrayList<ComImgTextInfo> infoList = new ArrayList<>();


        List<String> usernames = getUsernames();
        if (usernames != null) {
            for (String username : usernames) {
                ComImgTextInfo comImgTextInfo = new ComImgTextInfo();
                comImgTextInfo.setContentText(username);
                infoList.add(comImgTextInfo);
            }

            mUiChangeUserName.setOnClickListener(this);

        }


        mListWindow = new ListChooseWindow(this, infoList).createWindow();
        mListWindow.setBackgroundRes(R.drawable.light_grey_stroke_slide_bg);
        mListWindow.setCallBack(new CommCallBack() {
            @Override
            public void callBack(Object... object) {
                Integer position = (Integer) object[0];

                String text = infoList.get(position).getText();
                mUserName.setText(text);
                mUserName.setSelection(text.length());
            }

            @Override
            public void progress(String str1, String str2) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ui_change_user_name:
                WindowUtil.hideKeybord(mUserName, this);

                mListWindow.setWindowsWidth(mUserName.getMeasuredWidth());
                mListWindow.showDown(mUserName);
                break;

            case R.id.login:
                String ip = mServer.getText().toString().trim();
                String username = mUserName.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();

                if (TextUtils.isEmpty(ip)) {
                    showToast("请输入服务器地址");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    showToast("请输入用户名");
                    return;
                }

                if (TextUtils.isEmpty(ip)) {
                    showToast("请输入密码");
                    return;
                }

                if (!checkIp(ip)) {
                    showToast("ip设置不正确");
                    return;
                } else {

                    PreferencesUtils.putString(this, Consts.SERVER_IP, ip);

                }

                login(username, password);

                break;
            default:
                break;
        }

    }

    private void login(String username, String pwd) {


        if (isNetAvailable(this)) {

            loginByNet(username.trim(), pwd.trim());

        } else {

            if (PreferencesUtils.getBoolean(this, Consts.FIRST, true)) {
                showToast("请先联网同步数据");
                return;
            }
            loginByLocal(username, pwd);
        }


    }

    private void loginByLocal(String username, String pwd) {
        UserInfoDao userInfoDao = new UserInfoDao(this);
        boolean confirm = userInfoDao.confirm(username, MD5Util.getMD5(MD5Util.getMD5(pwd) + Const.LOGIN_SCERET));
        if (confirm) {
            doLoginSucceed(false, username);
        } else {
            showToast("登陆失败，用户名或密码错误！");
        }
    }

    private void loginByNet(final String username, final String pwd) {
        showProgress("正在登陆");
        RestApi.login(username, pwd, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d("RENRENREN","返回 错误信息 error = "+call.request().body().toString());
                e.printStackTrace();
                dismissProgress();
                showToast("网络出错，请检查。");
            }

            @Override
            public void onResponse(String response) {

                dismissProgress();
                Log.d("RENRENREN","返回response"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("result")) {
                        //TODO 登陆成功
                        JSONObject account = (JSONObject) jsonObject.get("account");
                        if (account != null) {
                            Log.d("RENRENREN","返回office_id= "+account.getString("office_id"));
                            Log.d("RENRENREN","login_name= "+account.getString("login_name"));
                            Log.d("RENRENREN","name= "+account.getString("name"));
//                            Gson gson = new Gson();
//                            UserInfo userInfo = gson.fromJson(data.toString(), UserInfo.class);
//                            UHFApplication.setUserInfo(userInfo);

                            String id = account.getString("id");
                            PreferencesUtils.putString(LoginActivity.this, Consts.ACCOUNT_ID, id);

                            String office_id = account.getString("office_id");
                            PreferencesUtils.putString(LoginActivity.this, Consts.OFFICE_ID, office_id);

                            String login_name = account.getString("login_name");
                            PreferencesUtils.putString(LoginActivity.this, Consts.LOGIN_NAME, login_name);

                            PreferencesUtils.putString(LoginActivity.this, Consts.PASSWORD, pwd);
                        }
                        doLoginSucceed(true, username);
                    }
                } catch (JSONException e) {

                    showToast("登陆出错，请检查。");

                }

            }
        });
    }


    public boolean checkIp(String ip) {
//        String ipAndPort = "((1?\\d{1,2}|2[1-5][1-5])\\.){3}(1?\\d{1,2}|2[1-5][1-5]):([1-5]?\\d{1,4}|6[1-5][1-5][1-3][1-5])";
//
////        String ipAndPort = "\\d{2,3}([.]\\d{1,3}){3}:\\d{2,5}";
//        Pattern ipAndPortPattern = Pattern.compile(ipAndPort);
//        String str = ip;
//
//
//        Matcher matcher = ipAndPortPattern.matcher(str);
//        return matcher.matches();

        return true ;


    }


    public void doLoginSucceed(boolean hasNet, String username) {

        putUsername(username);

        if (hasNet) {
            //有网登录
//            getUsers();
//            getAssetData();
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            //无网登录
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }

    private void getAssetData() {

        String update_time = PreferencesUtils.getString(this, Consts.UPDATE_TIME, "-1");

        RestApi.updateAssetData(update_time, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                Log.i("wuming", e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                String s = UnicodeUtil.decodeUnicode(response);
                AssetListResult assetListResult = new Gson().fromJson(s, AssetListResult.class);
                if (assetListResult.pageNum == 1) {
                    List<AssetInfo> data = assetListResult.data;
                    if (data != null) {
                        AssetInfoDao assetInfoDao = new AssetInfoDao(LoginActivity.this);
                        assetInfoDao.updateList(data);

                    }

                    getDeleteAssetData();

                }

            }
        });

    }


    private void getDeleteAssetData() {

        String update_time = PreferencesUtils.getString(this, Consts.UPDATE_TIME, "1000000000");

        RestApi.getAllDeleteAsset(update_time, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                dismissProgress();
                showToast("同步数据失败");
                Log.i("wuming", e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                dismissProgress();

                String s = UnicodeUtil.decodeUnicode(response);
                Log.i("wuming", s);
                AssetListResult assetListResult = new Gson().fromJson(s, AssetListResult.class);
                if (assetListResult.pageNum == 1) {

                     showToast("同步数据成功");

                    List<AssetInfo> data = assetListResult.data;
                    if (data != null) {
                        AssetInfoDao assetInfoDao = new AssetInfoDao(LoginActivity.this);
                        assetInfoDao.deleteList(data);
                    }


                }

            }
        });

    }

    public void getUsers() {

        showProgress("同步数据");
        RestApi.getUsers(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                dismissProgress();
                showToast("获取用户列表失败");
            }

            @Override
            public void onResponse(String response) {

                dismissProgress();
                String s = UnicodeUtil.decodeUnicode(response);

                UserListResult userListResult = new Gson().fromJson(s, UserListResult.class);
                if (userListResult.state == 1) {

                    List<UserInfo> data = userListResult.data;
                    if (data != null) {

                        UserInfoDao userInfoDao = new UserInfoDao(LoginActivity.this);
                        userInfoDao.deleteAll();
                        userInfoDao.addList(data);

                    }

                    PreferencesUtils.putBoolean(LoginActivity.this, Consts.FIRST, false);

                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);


                } else {
                    showToast("获取用户列表失败");

                }

            }
        });


    }


    public void putUsername(String username) {


        List<String> usernames = getUsernames();
        if (usernames != null && usernames.size() != 0) {


            if (!usernames.contains(username)) {
                StringBuilder stringBuilder = new StringBuilder();
                String usernameStr = PreferencesUtils.getString(this, Consts.USERNAMES, "");
                StringBuilder builder = stringBuilder.append(usernameStr).append(",").append(username);
                PreferencesUtils.putString(this, Consts.USERNAMES, builder.toString());
            }


        } else {
            PreferencesUtils.putString(this, Consts.USERNAMES, username);
        }


    }

    public List<String> getUsernames() {
        String usernames = PreferencesUtils.getString(this, Consts.USERNAMES, "");
        if (TextUtils.isEmpty(usernames)) {
            return null;
        } else {
            String[] split = usernames.split(",");
            return Arrays.asList(split);
        }


    }


}
