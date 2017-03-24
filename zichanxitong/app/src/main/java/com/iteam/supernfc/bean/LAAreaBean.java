package com.iteam.supernfc.bean;

public class LAAreaBean {

	
	public static final String ID = "_id";
	public static final String LA_AREANAME = "areaName";
	public static final String LA_AREAID = "areaId";
	
	
	private String areaName;
	private String areaId;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	@Override
	public String toString() {
		return "LAAreaBean [areaName=" + areaName + ", areaId=" + areaId + "]";
	}
	
	
	
}
