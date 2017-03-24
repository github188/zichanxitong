package com.iteam.supernfc.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteam.supernfc.bean.CountDeviceModel;
import com.iteam.supernfc.bean.LAAreaBean;
import com.iteam.supernfc.bean.LARoomBean;
import com.iteam.supernfc.bean.LAShelfBean;
import com.iteam.supernfc.bean.LAUListBean;
import com.iteam.supernfc.bean.NfcBean;

public class DataHelper {

	private SQLiteDatabase db;
	private NfcDbHandler dbHelper;

	public DataHelper(Context context) {
		dbHelper = NfcDbHandler.getInstance(context);
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
		dbHelper.close();
	}

	// 插入一条nfc数据到数据库
	public void addNfc(NfcBean bean) {

		db.execSQL(
				"insert into nfc_table (" + NfcBean.NFC_ASETNAME + ","
						+ NfcBean.NFC_CATEGORY + "," + NfcBean.NFC_AREA + ","
						+ NfcBean.NFC_ASETU + "," + NfcBean.NFC_SHELF + ","
						+ NfcBean.NFC_LABELID + "," + NfcBean.NFC_ROOM + ","
						+ NfcBean.NFC_UUU + "," + NfcBean.NFC_POSITIONTYPE
						+ "," + NfcBean.NFC_TYPE + "," + NfcBean.NFC_AREA_ID
						+ "," + NfcBean.NFC_ROOM_ID + ","
						+ NfcBean.NFC_SHELF_ID + "," + NfcBean.NFC_UU_ID
						+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { bean.getAssetName(), bean.getCategory(),
						bean.getArea(), bean.getAssetU(), bean.getShelf(),
						bean.getLabelId(), bean.getRoom(), bean.getUu(),
						bean.getNfcPosition(), bean.getNfcType(),
						bean.getArea_id(), bean.getRoom_id(),
						bean.getShelf_id(), bean.getAsset_u_id() });
	}
	// 插入一条nfc数据到数据库
	public void addQingDianNfc(NfcBean bean) {
		
		db.execSQL(
				"insert into qingdian_table (" + NfcBean.NFC_ASETNAME + ","
						+ NfcBean.NFC_CATEGORY + "," + NfcBean.NFC_AREA + ","
						+ NfcBean.NFC_ASETU + "," + NfcBean.NFC_SHELF + ","
						+ NfcBean.NFC_LABELID + "," + NfcBean.NFC_ROOM + ","
						+ NfcBean.NFC_UUU + "," + NfcBean.NFC_POSITIONTYPE
						+ "," + NfcBean.NFC_TYPE + "," + NfcBean.NFC_AREA_ID
						+ "," + NfcBean.NFC_ROOM_ID + ","
						+ NfcBean.NFC_SHELF_ID + "," + NfcBean.NFC_UU_ID
						+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { bean.getAssetName(), bean.getCategory(),
						bean.getArea(), bean.getAssetU(), bean.getShelf(),
						bean.getLabelId(), bean.getRoom(), bean.getUu(),
						bean.getNfcPosition(), bean.getNfcType(),
						bean.getArea_id(), bean.getRoom_id(),
						bean.getShelf_id(), bean.getAsset_u_id() });
	}

	// 存储用户名
	public void addUser(String userName) {

		db.execSQL("insert into nfc_user (username) values(?)",
				new Object[] { userName });
	}

	// 获取所有用户
	public List<String> getAllUsers() {
		List<String> beans = new ArrayList<String>();
		Cursor cursor = db.rawQuery("select * from nfc_user", null);

		while (cursor.moveToNext()) {
			// int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String userName = cursor.getString(cursor
					.getColumnIndex("username"));

			beans.add(userName);
		}
		return beans;
	}

	// 清空用户表
	public void clearUserTable() {
		try{
			
			db.execSQL("delete from nfc_user");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 存储区域位置信息
	public void addLAArea(List<LAAreaBean> areaList) {

		synchronized (dbHelper) {

			if (!db.isOpen()) {
				dbHelper.getReadableDatabase();
			}
			db.beginTransaction();
			try {
				for (int i = 0; i < areaList.size(); i++) {
					LAAreaBean bean = areaList.get(i);
					try {
						db.execSQL(
								"insert into nfc_area (" + LAAreaBean.LA_AREAID
										+ "," + LAAreaBean.LA_AREANAME
										+ ") values(?,?)",
								new Object[] { bean.getAreaId(),
										bean.getAreaName() });
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				// db.close();
			}
		}

	}

	// 存储机房信息
	public void addLARoom(List<LARoomBean> roomList) {

		synchronized (dbHelper) {

			if (!db.isOpen()) {
				dbHelper.getReadableDatabase();
			}
			db.beginTransaction();
			try {
				for (int i = 0; i < roomList.size(); i++) {

					LARoomBean bean = roomList.get(i);
					try {
						db.execSQL(
								"insert into nfc_room (" + LARoomBean.LA_RMPID
										+ "," + LARoomBean.LA_ROOMID + ","
										+ LARoomBean.LA_ROOMNAME
										+ ") values(?,?,?)",
								new Object[] { bean.getParentId(),
										bean.getRoomId(), bean.getRoomName() });
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				// db.close();
			}
		}

	}

	// 存储机柜
	public void addLAShelf(List<LAShelfBean> shelfList) {

		synchronized (dbHelper) {

			if (!db.isOpen()) {
				dbHelper.getReadableDatabase();
			}
			db.beginTransaction();
			try {
				for (int i = 0; i < shelfList.size(); i++) {
					LAShelfBean bean = shelfList.get(i);
					db.execSQL(
							"insert into nfc_shelf (" + LAShelfBean.LA_SFPID
									+ "," + LAShelfBean.LA_SHELFID + ","
									+ LAShelfBean.LA_SHELFNAME
									+ ") values(?,?,?)",
							new Object[] { bean.getParentId(),
									bean.getShelfId(), bean.getShelfName() });
				}
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				// db.close();
			}
		}

	}

	// 存储UU
	public void addLAUU(List<LAUListBean> uList) {

		synchronized (dbHelper) {

			if (!db.isOpen()) {
				dbHelper.getReadableDatabase();
			}
			db.beginTransaction();
			try {
				for (int i = 0; i < uList.size(); i++) {
					LAUListBean bean = uList.get(i);
					db.execSQL(
							"insert into nfc_uu (" + LAUListBean.LA_UID + ","
									+ LAUListBean.LA_UNAME + ","
									+ LAUListBean.LA_UPRID + ") values(?,?,?)",
							new Object[] { bean.getuId(), bean.getuName(),
									bean.getParentId() });
				}
				db.setTransactionSuccessful(); // 设置事务成功完成
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				// db.close();
			}
		}

	}

	public List<LAAreaBean> getAllArea() {
		List<LAAreaBean> beans = new ArrayList<LAAreaBean>();

		Cursor cursor = db.rawQuery("select * from nfc_area", null);
		while (cursor.moveToNext()) {

			// int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String areaName = cursor.getString(cursor
					.getColumnIndex("areaName"));
			String areaID = cursor.getString(cursor.getColumnIndex("areaId"));

			LAAreaBean arBean = new LAAreaBean();
			arBean.setAreaId(areaID);
			arBean.setAreaName(areaName);
			beans.add(arBean);
		}
		return beans;
	}

	// 根据区域ID获取机房
	public List<LARoomBean> getAllRoom(String areId) {
		List<LARoomBean> beans = new ArrayList<LARoomBean>();

		Cursor cursor = db.rawQuery(
				"select * from nfc_room where parentId = ?",
				new String[] { areId });
		while (cursor.moveToNext()) {

			// int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String roName = cursor.getString(cursor.getColumnIndex("roomName"));
			String roID = cursor.getString(cursor.getColumnIndex("roomId"));
			String roPID = cursor.getString(cursor.getColumnIndex("parentId"));

			LARoomBean rBean = new LARoomBean();
			rBean.setParentId(roPID);
			rBean.setRoomId(roID);
			rBean.setRoomName(roName);
			beans.add(rBean);
		}
		return beans;
	}

	// 根据机房ID获取机柜
	public List<LAShelfBean> getAllShelf(String sfId) {
		List<LAShelfBean> beans = new ArrayList<LAShelfBean>();

		Cursor cursor = db.rawQuery(
				"select * from nfc_shelf where parentId = ?",
				new String[] { sfId });
		while (cursor.moveToNext()) {

			// int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String sfName = cursor
					.getString(cursor.getColumnIndex("shelfName"));
			String sfID = cursor.getString(cursor.getColumnIndex("shelfId"));
			String sfPID = cursor.getString(cursor.getColumnIndex("parentId"));

			LAShelfBean sfBean = new LAShelfBean();
			sfBean.setParentId(sfPID);
			sfBean.setShelfId(sfID);
			sfBean.setShelfName(sfName);
			beans.add(sfBean);
		}
		return beans;
	}

	// 根据机柜ID获取UU位
	public List<LAUListBean> getAllUU(String uId) {
		List<LAUListBean> beans = new ArrayList<LAUListBean>();

		Cursor cursor = db.rawQuery("select * from nfc_uu where parentId = ?",
				new String[] { uId });
		while (cursor.moveToNext()) {

			// int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String uName = cursor.getString(cursor.getColumnIndex("uName"));
			String uID = cursor.getString(cursor.getColumnIndex("uId"));
			String uPID = cursor.getString(cursor.getColumnIndex("parentId"));

			LAUListBean uBean = new LAUListBean();
			uBean.setParentId(uPID);
			uBean.setuId(uID);
			uBean.setuName(uName);
			beans.add(uBean);
		}
		return beans;
	}

	// 清空表中数据
	public void clearLocation() {
		db.execSQL("delete from nfc_area");
		db.execSQL("delete from nfc_room");
		db.execSQL("delete from nfc_shelf");
		db.execSQL("delete from nfc_uu");
	}

	public List<NfcBean> getAllNfc() {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery("select * from nfc_table", null);
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}
	
	
	
	
	public List<NfcBean> getAllQingDianNfc() {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery("select * from qingdian_table", null);
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}
	
	
	

	// 根据type =1 获取本地未上传到服务器的数据
	public List<NfcBean> getAllNfcByType() {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery(
				"select * from nfc_table where nfc_type = 1 or nfc_type = 4",
				null);
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			
			String area_id = cursor.getString(cursor.getColumnIndex("area_id"));
			String room_id = cursor.getString(cursor.getColumnIndex("room_id"));
			String shelf_id = cursor.getString(cursor.getColumnIndex("shelf_id"));
			String uu_id = cursor.getString(cursor.getColumnIndex("asset_u_id"));
			
			
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			nfBean.setArea_id(area_id);
			nfBean.setRoom_id(room_id);
			nfBean.setShelf_id(shelf_id);
			nfBean.setAsset_u_id(uu_id);
			beans.add(nfBean);
		}
		return beans;
	}
	
	
	
	
	
	// 根据type =5，6，出库同步
		public List<NfcBean> getAllNfcByTypeChuKu() {
			List<NfcBean> beans = new ArrayList<NfcBean>();

			Cursor cursor = db.rawQuery(
					"select * from nfc_table where nfc_type = 5 or nfc_type = 6",
					null);
			while (cursor.moveToNext()) {

				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				String assetName = cursor.getString(cursor
						.getColumnIndex("assetName"));
				String category = cursor.getString(cursor
						.getColumnIndex("category"));
				String area = cursor.getString(cursor.getColumnIndex("area"));
				String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
				String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
				String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
				String room = cursor.getString(cursor.getColumnIndex("room"));
				String uu = cursor.getString(cursor.getColumnIndex("uu"));
				int nfcPosition = cursor.getInt(cursor
						.getColumnIndex("nfc_position"));
				int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
				NfcBean nfBean = new NfcBean();
				nfBean.setId(id);
				nfBean.setAssetName(assetName);
				nfBean.setCategory(category);
				nfBean.setArea(area);
				nfBean.setAssetU(assetU);
				nfBean.setShelf(shelf);
				nfBean.setLabelId(labelId);
				nfBean.setRoom(room);
				nfBean.setUu(uu);
				nfBean.setNfcType(nfcType);
				nfBean.setNfcPosition(nfcPosition);
				beans.add(nfBean);
			}
			return beans;
		}
	
	
	
	
	

	// 根据设备名，查找数据
	public List<NfcBean> getAllNfcByNames(String names) {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery(
				"select * from nfc_table where assetName = ?",
				new String[] { names });
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}
	
	
	// 根据设备名，查找数据
	public List<NfcBean> getAllNfcQingDianByNames(String names) {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery(
				"select * from qingdian_table where assetName = ?",
				new String[] { names });
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}
	
	

	// 根据type获取设备信息
	public List<NfcBean> getAllNfcByTypes(String typeVal) {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery(
				"select * from nfc_table where category = ?",
				new String[] { typeVal });
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}

	
	
	
	
	
	// 根据type获取设备信息
	public List<NfcBean> getAllNfcQingDianByTypes(String typeVal) {
		List<NfcBean> beans = new ArrayList<NfcBean>();

		Cursor cursor = db.rawQuery(
				"select * from qingdian_table where category = ?",
				new String[] { typeVal });
		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			beans.add(nfBean);
		}
		return beans;
	}

	
	// 按设备名称
	public List<CountDeviceModel> getDeviceNameAndNum() {

		List<CountDeviceModel> listVals = new ArrayList<CountDeviceModel>();

		Cursor cursor = db.rawQuery("select distinct assetName from nfc_table",
				null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			CountDeviceModel model = new CountDeviceModel();
			model.setName(name);
			listVals.add(model);
		}
		return listVals;
	}
	
	
	// 根据设备名获取该名下的设备数
		public List<CountDeviceModel> getDeviceNumCount(List<CountDeviceModel> listModels) {

			List<CountDeviceModel> listCount = new ArrayList<CountDeviceModel>();
			
			for(int i=0;i<listModels.size();i++){
				CountDeviceModel models = listModels.get(i);
				
				Cursor cursor = db.rawQuery("select count(*) from nfc_table where assetName = ? ",
						new String[] { models.getName() });
				cursor.moveToFirst();
				int numVal = cursor.getInt(0);
				models.setNum(numVal);
				listCount.add(models);
			}
			return listCount;
			
//			List<CountDeviceModel> listVals = new ArrayList<CountDeviceModel>();
//
//			Cursor cursor = db.rawQuery("select distinct assetName from nfc_table",
//					null);
//			while (cursor.moveToNext()) {
//				String name = cursor.getString(0);
//				CountDeviceModel model = new CountDeviceModel();
//				model.setName(name);
//				listVals.add(model);
//			}
//			return listVals;
		}
	
	
	
	// 按设备名称
	public List<String> getDeviceName() {

		List<String> listVals = new ArrayList<String>();

		Cursor cursor = db.rawQuery("select distinct assetName from nfc_table",
				null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			listVals.add(name);
		}
		return listVals;
	}

	// 按设备类型
	public List<String> getDeviceType() {
		List<String> listVals = new ArrayList<String>();
		Cursor cursor = db.rawQuery("select distinct category from nfc_table",
				null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			listVals.add(name);
		}

		return listVals;
	}

	public NfcBean getBean(String epc) {
		// 如果只对数据进行读取，建议使用此方法
		Cursor cursor = db.rawQuery(
				"select * from nfc_table where labelId=? and nfc_type=2",
				new String[] { epc });// 得到游标
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			return nfBean;
		}
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	//判断是否存在
	public NfcBean checkEPC(String epc) {
		// 如果只对数据进行读取，建议使用此方法
		Cursor cursor = db.rawQuery(
				"select * from nfc_table where labelId=? and (nfc_type in (1,3,4))",
				new String[] { epc });// 得到游标
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String assetName = cursor.getString(cursor
					.getColumnIndex("assetName"));
			String category = cursor.getString(cursor
					.getColumnIndex("category"));
			String area = cursor.getString(cursor.getColumnIndex("area"));
			String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
			String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
			String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			String uu = cursor.getString(cursor.getColumnIndex("uu"));
			int nfcPosition = cursor.getInt(cursor
					.getColumnIndex("nfc_position"));
			int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
			NfcBean nfBean = new NfcBean();
			nfBean.setId(id);
			nfBean.setAssetName(assetName);
			nfBean.setCategory(category);
			nfBean.setArea(area);
			nfBean.setAssetU(assetU);
			nfBean.setShelf(shelf);
			nfBean.setLabelId(labelId);
			nfBean.setRoom(room);
			nfBean.setUu(uu);
			nfBean.setNfcType(nfcType);
			nfBean.setNfcPosition(nfcPosition);
			return nfBean;
		}
		return null;
	}
	
	
	
	
	
	//判断是否存在
		public NfcBean checkChuKuEPC(String epc) {
			// 如果只对数据进行读取，建议使用此方法
			Cursor cursor = db.rawQuery(
					"select * from nfc_table where labelId=? and (nfc_type in (5,6,7))",
					new String[] { epc });// 得到游标
			if (cursor.moveToFirst()) {
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				String assetName = cursor.getString(cursor
						.getColumnIndex("assetName"));
				String category = cursor.getString(cursor
						.getColumnIndex("category"));
				String area = cursor.getString(cursor.getColumnIndex("area"));
				String assetU = cursor.getString(cursor.getColumnIndex("assetU"));
				String shelf = cursor.getString(cursor.getColumnIndex("shelf"));
				String labelId = cursor.getString(cursor.getColumnIndex("labelId"));
				String room = cursor.getString(cursor.getColumnIndex("room"));
				String uu = cursor.getString(cursor.getColumnIndex("uu"));
				int nfcPosition = cursor.getInt(cursor
						.getColumnIndex("nfc_position"));
				int nfcType = cursor.getInt(cursor.getColumnIndex("nfc_type"));
				NfcBean nfBean = new NfcBean();
				nfBean.setId(id);
				nfBean.setAssetName(assetName);
				nfBean.setCategory(category);
				nfBean.setArea(area);
				nfBean.setAssetU(assetU);
				nfBean.setShelf(shelf);
				nfBean.setLabelId(labelId);
				nfBean.setRoom(room);
				nfBean.setUu(uu);
				nfBean.setNfcType(nfcType);
				nfBean.setNfcPosition(nfcPosition);
				return nfBean;
			}
			return null;
		}
	
	
	
	
	
	public void updateType(int type, String epc) {
		db.beginTransaction();// 事启事务
		try {
			db.execSQL("update nfc_table set nfc_type=? where labelId=?",
					new Object[] { type, epc });
			db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
		} finally {
			db.endTransaction();
		}
	}

	
	
	// 清空表中数据
	public void clearTable() {
		db.execSQL("delete from nfc_table");
	}
	
	
	
	// 返回总条数
	public int getCount() {
		Cursor cursor = db.rawQuery(
				"select count(*) from nfc_table where nfc_type = 2", null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

}
