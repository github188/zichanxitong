package com.szcomtop.meal.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidVersions;
import com.fntech.m10a.gpio.M10A_GPIO;
import com.iteam.supernfc.bean.NfcBean;
import com.iteam.supernfc.db.DataHelper;
import com.iteam.supernfc.reader.CMD;
import com.iteam.supernfc.reader.model.ISO180006BOperateTagBuffer;
import com.iteam.supernfc.reader.model.InventoryBuffer;
import com.iteam.supernfc.reader.model.InventoryBuffer.InventoryTagMap;
import com.iteam.supernfc.reader.model.OperateTagBuffer;
import com.iteam.supernfc.reader.model.ReaderSetting;
import com.iteam.supernfc.reader.server.ReaderBase;
import com.iteam.supernfc.reader.server.ReaderHelper;
import com.iteam.supernfc.utils.M10_GPIO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import android_serialport_api.SerialPort;

public class ChuKuActivity extends Activity {

	private ReaderHelper mReaderHelper;
	private ReaderBase mReader;
	private SerialPort mSerialPort = null;

	private List<InventoryTagMap> data;

	private static ReaderSetting m_curReaderSetting;
	private static InventoryBuffer m_curInventoryBuffer;
	private static OperateTagBuffer m_curOperateTagBuffer;
	private static ISO180006BOperateTagBuffer m_curOperateTagISO18000Buffer;

	private LocalBroadcastManager lbm;

	private int mPos1 = -1, mPos2 = -1;

	private long mRefreshTime;
	private Date inventoryFrom = null;
	private String stringTxtRealInventoryCount = "";

	private TextView duquView, chukuView;
	private TextView titleLeft, titleRight;
	private ListView chukuListView;
	// private TextView labelView, nameView, typeView;

	private DataHelper dataHelper;
	// private NfcBean nfsBean;

	private List<String> listEpc;
	private List<NfcBean> listNfc;

	private int sign = 0;//0暂停，1读取

	// 设置最大功率
	/**
	 * 最大功率值
	 */
	private int endDBM = 33;



	@Override
	public void onResume() {
		if (mReader != null) {
			if (!mReader.IsAlive())
				mReader.StartWait();
		}
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


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

		lbm = LocalBroadcastManager.getInstance(ChuKuActivity.this);

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
		try {
			String powerStr = endDBM + "dBm";
			btOutputPower = (byte) Integer.parseInt(powerStr.subSequence(0,
					powerStr.indexOf("dBm")).toString());
		} catch (Exception e) {
			;
		}
		int ret = mReader.setOutputPower(m_curReaderSetting.btReadId,
				btOutputPower);
		if (ret == 0) {
			m_curReaderSetting.btAryOutputPower = new byte[] { btOutputPower };
			// Toast.makeText(getApplicationContext(),
			// getResources().getString(R.string.str_setting_out_power_success),
			// Toast.LENGTH_SHORT).show();
		} else {
			// Toast.makeText(getApplicationContext(),
			// getResources().getString(R.string.str_setting_out_power_fail),
			// Toast.LENGTH_SHORT).show();
		}

		dataHelper = new DataHelper(ChuKuActivity.this);

		startInventory();

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
		mSerialPort.close();
		if (android.os.Build.VERSION.RELEASE.equals(AndroidVersions.V_4_0_3)) {
			M10_GPIO.R1000_PowerOFF();
		} else if (android.os.Build.VERSION.RELEASE
				.equals(AndroidVersions.V_5_1_1)) {
			M10A_GPIO.PowerOff();
		}
		// System.exit(0);
	}

	private Handler mHandler = new Handler();
	private Runnable mRefreshRunnable = new Runnable() {
		public void run() {
			// refreshList();
			// refreshText();
			mHandler.postDelayed(this, 2000);
		}
	};

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

	private Runnable mRunnable = new Runnable() {
		public void run() {
			byte btWorkAntenna = m_curInventoryBuffer.lAntenna
					.get(m_curInventoryBuffer.nIndexAntenna);
			if (btWorkAntenna < 0)
				btWorkAntenna = 0;

			mReader.setWorkAntenna(m_curReaderSetting.btReadId, btWorkAntenna);
		}
	};

	int refresh_ui_cycle = 0;
	int refresh_ui_cycle_T = 4;
	private final BroadcastReceiver mRecv = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					ReaderHelper.BROADCAST_REFRESH_INVENTORY_REAL)) {
				byte btCmd = intent.getByteExtra("cmd", (byte) 0x00);

				switch (btCmd) {
					case CMD.REAL_TIME_INVENTORY:
					case CMD.CUSTOMIZED_SESSION_TARGET_INVENTORY:

						// long llid=Thread.currentThread().getId();
						// llid+=0;

					/*
					 * if(refresh_ui_cycle%(refresh_ui_cycle_T)==0) {
					 * refreshText(); } refresh_ui_cycle++;
					 * if(refresh_ui_cycle==refresh_ui_cycle_T+1) {
					 * refresh_ui_cycle=0; }
					 */
						// refreshText();

						Log.e("wang", "..w22.recyle..."
								+ m_curInventoryBuffer.lsTagList.toString());

						// mLoopHandler.removeCallbacks(mLoopRunnable);
						// mLoopHandler.postDelayed(mLoopRunnable, 2000);
						break;
					case ReaderHelper.INVENTORY_ERR:
					case ReaderHelper.INVENTORY_ERR_END:
					case ReaderHelper.INVENTORY_END:
						if (mReaderHelper.getInventoryFlag()) {
							// mLoopHandler.removeCallbacks(mLoopRunnable);
							// mLoopHandler.postDelayed(mLoopRunnable, 2000);
							// 防止盘询失败
							// mLoopHandler.removeCallbacks(mRunnable);
							// mLoopHandler.postDelayed(mRunnable, 2000);
						} else {
							mLoopHandler.removeCallbacks(mLoopRunnable);
						}
						// refreshList();

//					 Log.e("wang", "....end.....");
						break;
				}

			} else if (intent.getAction().equals(
					ReaderHelper.BROADCAST_WRITE_LOG)) {

			}
		}
	};



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



//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//			case R.id.title_right:
//				Intent locationIntent = new Intent(ChuKuActivity.this,
//						LocationActivity.class);
//				startActivity(locationIntent);
//				stopInventory();
//				break;
//			case R.id.title_left:
//				finish();
//				break;
//			case R.id.chuku_sum_chuku:
//
//				if (listEpc == null || listEpc.size() == 0) {
//					Toast.makeText(ChuKuActivity.this, "请先扫描标签,再出库操作...",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				sign = 0;
//				stopInventory();
//
//				Toast.makeText(ChuKuActivity.this, "出库成功...", Toast.LENGTH_SHORT)
//						.show();
//				break;
//			case R.id.chuku_sum_chuku_read:
//				startInventory();
//				sign = 1;
//				// R.string.inventoryCountText
//
//				break;
//		}
//
//	}


	public void startInventory(){
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

		mRefreshTime = new Date().getTime();

		// mLoopHandler.removeCallbacks(mLoopRunnable);
		// mLoopHandler.postDelayed(mLoopRunnable, 2000);
		mHandler.removeCallbacks(mRefreshRunnable);
		mHandler.postDelayed(mRefreshRunnable, 500);
	}

	public void stopInventory() {
		// refreshText();
		mReaderHelper.setInventoryFlag(false);
		m_curInventoryBuffer.bLoopInventory = false;
		m_curInventoryBuffer.bLoopInventoryReal = false;

		mLoopHandler.removeCallbacks(mLoopRunnable);
		mHandler.removeCallbacks(mRefreshRunnable);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		EndWork();

		stopInventory();

		if (lbm != null)
			lbm.unregisterReceiver(mRecv);

		// mLoopHandler.removeCallbacks(mLoopRunnable);
		// mHandler.removeCallbacks(mRefreshRunnable);

		if (listNfc != null) {
			listNfc.clear();
			listNfc = null;
		}

		if (listEpc != null) {
			listEpc.clear();
			listEpc = null;
		}

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
			case 80:
				if(sign == 0){
					startInventory();
					sign = 1;
				}else{
					stopInventory();
					sign = 0;
				}

				break;

			default:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 网络检查
	public boolean checkHasWifi() {
		try {
			ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
			boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnectedOrConnecting();
			boolean internet = con.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
			if (!wifi && !internet) {
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}

}
