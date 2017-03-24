package com.iteam.supernfc.bean;

public class LARoomBean {

	public static final String ID = "_id";
	public static final String LA_ROOMNAME = "roomName";
	public static final String LA_ROOMID = "roomId";
	public static final String LA_RMPID = "parentId";
	
	private String parentId;
	private String roomName;
	private String roomId;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	@Override
	public String toString() {
		return "LARoomBean [parentId=" + parentId + ", roomName=" + roomName
				+ ", roomId=" + roomId + "]";
	}
	
	
	
}
