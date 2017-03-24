package com.szcomtop.meal.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidVersions;
import com.example.sortlistview.*;
import com.fntech.m10a.gpio.M10A_GPIO;
import com.google.gson.Gson;
import com.iteam.supernfc.reader.CMD;
import com.iteam.supernfc.reader.model.ISO180006BOperateTagBuffer;
import com.iteam.supernfc.reader.model.InventoryBuffer;
import com.iteam.supernfc.reader.model.OperateTagBuffer;
import com.iteam.supernfc.reader.model.ReaderSetting;
import com.iteam.supernfc.reader.server.ReaderBase;
import com.iteam.supernfc.reader.server.ReaderHelper;
import com.iteam.supernfc.utils.M10_GPIO;
import com.j256.ormlite.stmt.query.In;
import com.szcomtop.meal.Adapter.AssetCountAdapter;
import com.szcomtop.meal.Dao.AssetInfoDao;
import com.szcomtop.meal.R;
import com.szcomtop.meal.common.CommCallBack;
import com.szcomtop.meal.common.CommonConfirmDialog;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.ComImgTextInfo;
import com.szcomtop.meal.model.CommonOperateResult;
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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.SerialPort;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.j256.ormlite.field.DataPersisterManager.clear;

/**
 * Created by wuming on 16/11/16.
 */
public class CommonOperateActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final int TYPE_REGISTE = 1; //注册
    public static final int TYPE_OUT = 2; //出库、领用
    public static final int TYPE_OUT2 = 3; //出库、领用
    public static final int TYPE_TRANSFER = 4; //调拨
    public static final int TYPE_SCRAP = 5;  // 报废
    public static final int TYPE_BORROW = 6; //借用
    public static final int TYPE_DAMAGED = 7; // 报损
    public static final int TYPE_RETURN = 8; //退还
    public static final int TYPE_STOCK = 9; //盘点


    public static final boolean isPhone = false;

    private ListView listView;

    private List<AssetInfo> scanData = new ArrayList<>();
    private List<AssetInfo> showData = new ArrayList<>();




    public SerialPort mSerialPort;
    private ReaderHelper mReaderHelper;
    private ReaderBase mReader;
    private ReaderSetting m_curReaderSetting;
    private OperateTagBuffer m_curOperateTagBuffer;
    private InventoryBuffer m_curInventoryBuffer;
    private ISO180006BOperateTagBuffer m_curOperateTagISO18000Buffer;
    private LocalBroadcastManager lbm;


    private AssetCountAdapter adapter;

    public long mLastMill = 0;



    private int sign = 0;//0暂停，1读取

    // 设置最大功率
    /**
     * 最大功率值
     */
    private int endDBM = 33;

    private Handler mLoopHandler = new Handler();
    private Runnable mLoopRunnable = new Runnable() {
        public void run() {
            byte btWorkAntenna = m_curInventoryBuffer.lAntenna
                    .get(m_curInventoryBuffer.nIndexAntenna);
            if (btWorkAntenna < 0)
                btWorkAntenna = 0;

            mReader.setWorkAntenna(m_curReaderSetting.btReadId, btWorkAntenna);
            mLoopHandler.postDelayed(this, 2000);
        }
    };
    private TextView startBtn;
    private TextView clearBtn;
    private TextView emptyTv;
    private TextView okBtn;
    private Button mRightBtn;
    private String title;
    private int type;
    private OperateCallBack operateCallBack;
    private ImageView mPandianArrow;
    private View mPandianArea;
    private EditText mPandianNumEt;
    private ImageView mPandianRefresh;
    private ListChooseWindow mListWindow;
    private Animation mAnimation;
    private TextView mBorrowerTv;
    private String mBorrowId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_operare);

        endDBM = PreferencesUtils.getInt(this, Consts.D_BM, 33);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        type = intent.getIntExtra("type", 0);

        initView();


        if (!isPhone) {
            initReader();

        }


    }


    @Override
    public void onResume() {
        if (mReader != null) {
            if (!mReader.IsAlive())
                mReader.StartWait();
        }


        super.onResume();
    }

    private void initView() {

        View emptyView = findViewById(R.id.empty_view);
        emptyTv = ((TextView) emptyView.findViewById(R.id.empty_view_tv));
        adapter = new AssetCountAdapter(this, showData);
        listView = (ListView) findViewById(R.id.common_operate_listview);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        startBtn = (TextView) findViewById(R.id.common_operate_start_btn);
        okBtn = (TextView) findViewById(R.id.common_operate_ok_btn);
        clearBtn = (TextView) findViewById(R.id.common_operate_clear_btn);


        mPandianArrow = (ImageView) findViewById(R.id.pandian_arrow);
        mPandianArea = findViewById(R.id.pandian_num_area);
        mPandianNumEt = (EditText) findViewById(R.id.pandian_num_et);
        mPandianRefresh = (ImageView) findViewById(R.id.pandian_refresh);

        mBorrowerTv = (TextView) findViewById(R.id.common_operate_borrower_tv);

        startBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);


    }

    @Override
    public int getHeaderType() {
        return HeadView.TYPE_LRIGHTBTN;
    }


    @Override
    public void initRightBtn(Button rBtn) {


        if (sign == 0 && showData.size() > 0 && type != TYPE_STOCK){
            rBtn.setVisibility(View.VISIBLE);
        }else {
            rBtn.setVisibility(View.INVISIBLE);

        }
        rBtn.setText(adapter.isSelectAll() ? "非全选" : "全选");

        mRightBtn = rBtn;

    }


    @Override
    public void initCenterTv(TextView cTv) {

        cTv.setText(title);
    }

    @Override
    public void OnRightBtnClick(View v) {

        adapter.selectAll(!adapter.isSelectAll());
        mRightBtn.setText(adapter.isSelectAll() ? "非全选" : "全选");

    }

    private void initReader() {

        PrepareWork();
        sign = 0;
        try {
            mReaderHelper = ReaderHelper.getDefaultHelper();
            mReaderHelper.setReader(mSerialPort.getInputStream(),
                    mSerialPort.getOutputStream());
            mReader = mReaderHelper.getReader();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        m_curReaderSetting = mReaderHelper.getCurReaderSetting();
        m_curInventoryBuffer = mReaderHelper.getCurInventoryBuffer();
        m_curOperateTagBuffer = mReaderHelper.getCurOperateTagBuffer();
        m_curOperateTagISO18000Buffer = mReaderHelper
                .getCurOperateTagISO18000Buffer();

        lbm = LocalBroadcastManager.getInstance(this);

        IntentFilter itent = new IntentFilter();
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_FAST_SWITCH);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_INVENTORY);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_ISO18000_6B);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_OPERATE_TAG);
        itent.addAction(ReaderHelper.BROADCAST_REFRESH_READER_SETTING);
        itent.addAction(ReaderHelper.BROADCAST_WRITE_LOG);
        itent.addAction(ReaderHelper.BROADCAST_WRITE_DATA);

        lbm.registerReceiver(mRecv, itent);

        mReader.getOutputPower(m_curReaderSetting.btReadId);

        byte btOutputPower = 0x00;

        btOutputPower = (byte) endDBM;

        int ret = mReader.setOutputPower(m_curReaderSetting.btReadId,
                btOutputPower);
        if (ret == 0) {
            m_curReaderSetting.btAryOutputPower = new byte[]{btOutputPower};

        } else {

        }


    }


    /**
     * 模块上电，打开串口
     */
    private void PrepareWork() {
        if (android.os.Build.VERSION.RELEASE.equals(AndroidVersions.V_4_0_3)) {
            M10_GPIO.R1000_PowerOn(); // 模块上电
            try {
                mSerialPort = new SerialPort(new File("dev/ttySAC2"), 115200, 0);
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (android.os.Build.VERSION.RELEASE
                .equals(AndroidVersions.V_5_1_1)) {
            M10A_GPIO.PowerOn(); // 模块上电
            try {
                M10A_GPIO._uhf_SwitchSerialPort();
                mSerialPort = new SerialPort(new File("/dev/ttyHSL0"), 115200,
                        0);
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "程序版本有误，请联系技术支持人员！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 模块下电，关闭串口
     */
    private void EndWork() {

        if (mSerialPort != null) {

            mSerialPort.close();
        }
        if (android.os.Build.VERSION.RELEASE.equals(AndroidVersions.V_4_0_3)) {
            M10_GPIO.R1000_PowerOFF();
        } else if (android.os.Build.VERSION.RELEASE
                .equals(AndroidVersions.V_5_1_1)) {
            M10A_GPIO.PowerOff();
        }
        // System.exit(0);
    }


    public void startInventory() {

        if (isPhone) {

            return;
        }

        sign = 1 ;
        // Loger.disk_log("调试", "开始盘询，设置天线", "M10_U8");
        m_curInventoryBuffer.clearInventoryPar();

        m_curInventoryBuffer.bLoopCustomizedSession = true;
        /*
         * m_curInventoryBuffer.btSession = 0x00;
		 * m_curInventoryBuffer.btTarget = 0x00;
		 */

        m_curInventoryBuffer.btSession = (byte) (getSessionState() & 0xFF);
        m_curInventoryBuffer.btTarget = (byte) (getFlagState() & 0xFF);

        m_curInventoryBuffer.lAntenna.add((byte) 0x01);

        m_curInventoryBuffer.bLoopInventoryReal = true;
        // m_curInventoryBuffer.btRepeat = 0;

        m_curInventoryBuffer.btRepeat = (byte) 1;

        // m_curInventoryBuffer.bLoopCustomizedSession = false;

        m_curInventoryBuffer.clearInventoryRealResult();
        mReaderHelper.setInventoryFlag(true);
        mReaderHelper.clearInventoryTotal();

        byte btWorkAntenna = m_curInventoryBuffer.lAntenna
                .get(m_curInventoryBuffer.nIndexAntenna);
        if (btWorkAntenna < 0)
            btWorkAntenna = 0;

        mReader.setWorkAntenna(m_curReaderSetting.btReadId, btWorkAntenna);
        // Loger.disk_log("调试", "开始盘询，天线设置完毕", "M10_U8");
        m_curReaderSetting.btWorkAntenna = btWorkAntenna;

//        mHandler.removeCallbacks(mRefreshRunnable);
        mLoopHandler.postDelayed(mLoopRunnable, 2000);

    }

    public void stopInventory() {

        if (isPhone) {
            return;
        }
        sign = 0 ;
        // refreshText();
        mReaderHelper.setInventoryFlag(false);
        m_curInventoryBuffer.bLoopInventory = false;
        m_curInventoryBuffer.bLoopInventoryReal = false;
        mLoopHandler.removeCallbacks(mLoopRunnable);


    }

    public int getSessionState() {
        SharedPreferences spf = getSharedPreferences("setting",
                Activity.MODE_PRIVATE);
        int state = spf.getInt("_session", 0);
        return state;
    }

    public int getFlagState() {
        SharedPreferences spf = getSharedPreferences("setting",
                Activity.MODE_PRIVATE);
        int state = spf.getInt("_flag", 0);
        return state;
    }

    private final BroadcastReceiver mRecv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL)) {
                byte btCmd = intent.getByteExtra("cmd", (byte) 0x00);


                switch (btCmd) {
                    case CMD.REAL_TIME_INVENTORY:
                    case CMD.CUSTOMIZED_SESSION_TARGET_INVENTORY:


                        Log.e("wang", "..w22.recyle..."
                                + m_curInventoryBuffer.lsTagList.toString());

                        break;
                    case ReaderHelper.INVENTORY_ERR:
                    case ReaderHelper.INVENTORY_ERR_END:
                    case ReaderHelper.INVENTORY_END:


                        if (mReaderHelper.getInventoryFlag()) {

                        } else {
                            mLoopHandler.removeCallbacks(mLoopRunnable);
                        }


                        //TODO 以下有性能问题
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                scanData.clear();
                                List<InventoryBuffer.InventoryTagMap> lsTagList = m_curInventoryBuffer.lsTagList;
                                for (InventoryBuffer.InventoryTagMap inventoryTagMap : lsTagList) {

                                    AssetInfo assetInfo = new AssetInfo();
                                    assetInfo.rfid_code = inventoryTagMap.strEPC.replace(" ", "");
                                    scanData.add(assetInfo);

                                }
                                AssetInfoDao assetInfoDao = new AssetInfoDao(CommonOperateActivity.this);
                                assetInfoDao.refreshList(scanData);
                                mLoopHandler.post(new Runnable() {

                                    @Override
                                    public void run() {

                                        if (System.currentTimeMillis() - mLastMill > 100){

                                            showData.clear();
                                            showData.addAll(scanData);
                                            adapter.notifyDataSetChanged();
                                            mLastMill = System.currentTimeMillis() ;

                                        }


                                    }
                                });
                            }
                        }).start();

                        break;


                }

            } else if (intent.getAction().equals(
                    ReaderHelper.BROADCAST_WRITE_LOG)) {

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.common_operate_start_btn:

                onStartBtnClick();

                break;
            case R.id.common_operate_ok_btn:

                onOkBtnClick();

                break;
            case R.id.common_operate_clear_btn:

                onClearBtnClick();

                break;



        }
    }



    private void onStartBtnClick() {

        if (startBtn.getText().toString().equals("开始")) {

            startInventory();
            emptyTv.setText("正在扫描，请将手持机对准标签...");
            startBtn.setText("停止");

            adapter.setShowCheckbox(false);
            mRightBtn.setVisibility(View.INVISIBLE);
            okBtn.setEnabled(false);

        } else {

            showData.clear();
            showData.addAll(scanData);
            adapter.notifyDataSetChanged();

            stopInventory();
            emptyTv.setText("已停止扫描，点击开始启动扫描");

            startBtn.setText("开始");

            if (type != TYPE_STOCK){

                adapter.setShowCheckbox(true);
                mRightBtn.setVisibility(View.VISIBLE);
                mRightBtn.setText("全选");
            }else {

                mRightBtn.setVisibility(View.INVISIBLE);

            }


            okBtn.setEnabled(true);


        }


    }


    public void onOkBtnClick() {




        if (adapter == null) {
            return;
        }

        List<AssetInfo> selectedInfos = adapter.getSelectedInfos();

        if (type == TYPE_STOCK){

            selectedInfos = scanData ;

        }

        List<String> ids = new ArrayList<>();
//       ids.add("E200001529170046202044A2");
//       ids.add("E2000015291700292020445E");
//       ids.add("E200001529170029202affafa");
//       ids.add("E200001529170029202afafsfakaj");
//       ids.add("E200001529170029202affaf89saf8");
        if (selectedInfos == null || selectedInfos.size() == 0) {

            showToast("未选择");
            return;
        }

        if (operateCallBack == null) {

            operateCallBack = new OperateCallBack();
        }

        String operateId = PreferencesUtils.getString(this, Consts.OPERATE_ID);
        for (AssetInfo selectedInfo : selectedInfos) {
            ids.add(selectedInfo.rfid_code);
        }

        Intent intent = new Intent(this, OptionActivity.class);
        intent.putExtra("data", (Serializable) selectedInfos);
        intent.putExtra("type",type);
        intent.putExtra("title",title);

        switch (type) {

            case TYPE_REGISTE:

                startActivity(intent);


//                showProgress("处理中...");
//
//                RestApi.assetRegiste(ids, operateId, operateCallBack);

                break;

            case TYPE_OUT:

                showProgress("处理中...");

                RestApi.assetOutStorage(ids, operateId, operateCallBack);

                break;
            case TYPE_OUT2:


                startActivity(intent);

                break;

            case TYPE_TRANSFER:

                intent.putExtra("data", (Serializable) selectedInfos);
                intent.putExtra("type",type);
                startActivity(intent);

                break;
            case TYPE_SCRAP:

                showProgress("处理中...");

                RestApi.assetScrap(ids, operateId, operateCallBack);

                break;
            case TYPE_BORROW:

                intent.putExtra("data", (Serializable) selectedInfos);
                intent.putExtra("type",type);
                startActivity(intent);


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

                intent.putExtra("data", (Serializable) selectedInfos);
                intent.putExtra("type",type);
                startActivity(intent);


                break;
            default:
                break;


        }

    }


    public void onClearBtnClick() {

        showData.clear();
        adapter.notifyDataSetChanged();

        stopInventory();
        emptyTv.setText("已停止扫描，点击开始启动扫描");

        startBtn.setText("开始");


    }


    @Override
    public void onDestroy() {


        stopInventory();
        // EndWork();


        if (lbm != null)
            lbm.unregisterReceiver(mRecv);

        super.onDestroy();

    }









    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        adapter.changeSelect(position);

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

                        final AssetInfoDao assetInfoDao = new AssetInfoDao(CommonOperateActivity.this);


                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                assetInfoDao.refreshList(data);
                                mLoopHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(CommonOperateActivity.this, ResultActivity.class);
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


    @Override
    public void OnLeftBtnClick(View v) {

        onBackPressed();
    }

    @Override
    public void onBackPressed() {



            CommonConfirmDialog dialog = new CommonConfirmDialog(this, "请确定", "确定要返回吗？", "取消", "确定");
            dialog.setPositiveButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            dialog.show();


    }
}
