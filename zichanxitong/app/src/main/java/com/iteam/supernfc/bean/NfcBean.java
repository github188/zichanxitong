package com.iteam.supernfc.bean;

public class NfcBean {
	
	public static final String ID = "_id";
	public static final String NFC_ASETNAME = "assetName";
	public static final String NFC_CATEGORY = "category";
	public static final String NFC_AREA = "area";
	public static final String NFC_ASETU = "assetU";
	public static final String NFC_SHELF = "shelf";
	public static final String NFC_LABELID = "labelId";
	public static final String NFC_ROOM = "room";
	public static final String NFC_UUU = "uu";
	public static final String NFC_POSITIONTYPE = "nfc_position";//为2表示已经记录位置,1表示未记录位置
	public static final String NFC_TYPE = "nfc_type";//为1表示走本地读取的，需要上传到服务器，为2表示走服务器拉取下来的,（3表示上传到服务器，上传成功）,为4表示上传数据失败,为5表示出库,为6表示出库失败,7表示出库成功

	public static final String NFC_AREA_ID ="area_id";
	public static final String NFC_ROOM_ID ="room_id";
	public static final String NFC_SHELF_ID ="shelf_id";
	public static final String NFC_UU_ID ="asset_u_id";
	
	
	private int id;
	private String assetName;
	private String category;
	private String area;
	private String assetU;
	private String shelf;
	private String labelId;
	private String room;
	private String uu;
	private int nfcType;
	private int nfcPosition;
	
	private String area_id;
	private String room_id;
	private String shelf_id;
	private String asset_u_id;
	
	
	
	
	
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getShelf_id() {
		return shelf_id;
	}
	public void setShelf_id(String shelf_id) {
		this.shelf_id = shelf_id;
	}
	public String getAsset_u_id() {
		return asset_u_id;
	}
	public void setAsset_u_id(String asset_u_id) {
		this.asset_u_id = asset_u_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAssetU() {
		return assetU;
	}
	public void setAssetU(String assetU) {
		this.assetU = assetU;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getUu() {
		return uu;
	}
	public void setUu(String uu) {
		this.uu = uu;
	}
	public int getNfcType() {
		return nfcType;
	}
	public void setNfcType(int nfcType) {
		this.nfcType = nfcType;
	}
	public int getNfcPosition() {
		return nfcPosition;
	}
	public void setNfcPosition(int nfcPosition) {
		this.nfcPosition = nfcPosition;
	}
	@Override
	public String toString() {
		return "NfcBean [id=" + id + ", assetName=" + assetName + ", category="
				+ category + ", area=" + area + ", assetU=" + assetU
				+ ", shelf=" + shelf + ", labelId=" + labelId + ", room="
				+ room + ", uu=" + uu + ", nfcType=" + nfcType
				+ ", nfcPosition=" + nfcPosition + "]";
	}
	
		
	
}
