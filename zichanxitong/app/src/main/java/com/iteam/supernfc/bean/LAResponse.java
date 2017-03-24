package com.iteam.supernfc.bean;

import java.util.List;

public class LAResponse {
	
	private List<LAShelfBean> shelfList;
	private List<LARoomBean> roomList;
	private List<LAAreaBean> areaList;
	private List<LAUListBean> uList;
	public List<LAShelfBean> getShelfList() {
		return shelfList;
	}
	public void setShelfList(List<LAShelfBean> shelfList) {
		this.shelfList = shelfList;
	}
	public List<LARoomBean> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<LARoomBean> roomList) {
		this.roomList = roomList;
	}
	public List<LAAreaBean> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<LAAreaBean> areaList) {
		this.areaList = areaList;
	}
	public List<LAUListBean> getuList() {
		return uList;
	}
	public void setuList(List<LAUListBean> uList) {
		this.uList = uList;
	}
	@Override
	public String toString() {
		return "LAResponse [shelfList=" + shelfList + ", roomList=" + roomList
				+ ", areaList=" + areaList + ", uList=" + uList + "]";
	}
	
	
	
	

}
