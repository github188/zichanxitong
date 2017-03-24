package com.iteam.supernfc.bean;

public class LAShelfBean {
	
	public static final String ID = "_id";
	public static final String LA_SHELFNAME = "shelfName";
	public static final String LA_SHELFID = "shelfId";
	public static final String LA_SFPID = "parentId";

	private String parentId;
	private String shelfId;
	private String shelfName;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getShelfId() {
		return shelfId;
	}
	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}
	public String getShelfName() {
		return shelfName;
	}
	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}
	@Override
	public String toString() {
		return "LAShelfBean [parentId=" + parentId + ", shelfId=" + shelfId
				+ ", shelfName=" + shelfName + "]";
	}
	
	
	
	
}
