package com.iteam.supernfc.bean;

public class LAUListBean {
	
	
	public static final String ID = "_id";
	public static final String LA_UPRID = "parentId";
	public static final String LA_UNAME = "uName";
	public static final String LA_UID = "uId";
	

	private String parentId;
	private String uName;
	private String uId;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	@Override
	public String toString() {
		return "LAUListBean [parentId=" + parentId + ", uName=" + uName
				+ ", uId=" + uId + "]";
	}
	
	
	
	
	
	
}
