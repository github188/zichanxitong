package com.iteam.supernfc.db;

import com.iteam.supernfc.bean.LAAreaBean;
import com.iteam.supernfc.bean.LARoomBean;
import com.iteam.supernfc.bean.LAShelfBean;
import com.iteam.supernfc.bean.LAUListBean;
import com.iteam.supernfc.bean.NfcBean;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NfcDbHandler extends SQLiteOpenHelper {

	private static final String DATABASENAME = "test.db"; // 数据库名称
	private static final int DATABASEVERSION = 1;// 数据库版本,大于0

	public NfcDbHandler(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
		// TODO Auto-generated constructor stub
	}
	



	private static NfcDbHandler mInstance;

	public synchronized static NfcDbHandler getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NfcDbHandler(context);
		}
		return mInstance;
	};

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 创建表

		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_table" + "("
				+ NfcBean.ID + " integer primary key," + NfcBean.NFC_ASETNAME
				+ " varchar," + NfcBean.NFC_CATEGORY + " varchar,"
				+ NfcBean.NFC_AREA + " varchar," + NfcBean.NFC_ASETU
				+ " varchar," + NfcBean.NFC_SHELF + " varchar,"
				+ NfcBean.NFC_LABELID + " varchar," + NfcBean.NFC_ROOM + " varchar,"
				+ NfcBean.NFC_AREA_ID + " varchar," + NfcBean.NFC_ROOM_ID + " varchar,"
				+ NfcBean.NFC_SHELF_ID + " varchar," + NfcBean.NFC_UU_ID
				+ " varchar," + NfcBean.NFC_UUU + " varchar,"
				+ NfcBean.NFC_POSITIONTYPE + " integer," + NfcBean.NFC_TYPE
				+ " integer" + ")");
		
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "qingdian_table" + "("
				+ NfcBean.ID + " integer primary key," + NfcBean.NFC_ASETNAME
				+ " varchar," + NfcBean.NFC_CATEGORY + " varchar,"
				+ NfcBean.NFC_AREA + " varchar," + NfcBean.NFC_ASETU
				+ " varchar," + NfcBean.NFC_SHELF + " varchar,"
				+ NfcBean.NFC_LABELID + " varchar," + NfcBean.NFC_ROOM + " varchar,"
				+ NfcBean.NFC_AREA_ID + " varchar," + NfcBean.NFC_ROOM_ID + " varchar,"
				+ NfcBean.NFC_SHELF_ID + " varchar," + NfcBean.NFC_UU_ID
				+ " varchar," + NfcBean.NFC_UUU + " varchar,"
				+ NfcBean.NFC_POSITIONTYPE + " integer," + NfcBean.NFC_TYPE
				+ " integer" + ")");
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_area" + "("
				+ LAAreaBean.ID + " integer primary key,"
				+ LAAreaBean.LA_AREANAME + " varchar," + LAAreaBean.LA_AREAID
				+ " varchar" + ")");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_room" + "("
				+ LARoomBean.ID + " integer primary key," + LARoomBean.LA_RMPID
				+ " varchar," + LARoomBean.LA_ROOMID + " varchar,"
				+ LARoomBean.LA_ROOMNAME + " varchar" + ")");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_shelf" + "("
				+ LAShelfBean.ID + " integer primary key,"
				+ LAShelfBean.LA_SFPID + " varchar," + LAShelfBean.LA_SHELFID
				+ " varchar," + LAShelfBean.LA_SHELFNAME + " varchar" + ")");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_uu" + "("
				+ LAUListBean.ID + " integer primary key," + LAUListBean.LA_UID
				+ " varchar," + LAUListBean.LA_UNAME + " varchar,"
				+ LAUListBean.LA_UPRID + " varchar" + ")");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + "nfc_user" + "("
				+ LAUListBean.ID + " integer primary key," + "username"
				+ " varchar" + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
