package com.iteam.supernfc.bean;

import java.util.List;

public class UserNameBean {

	private List<String> userList;

	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "UserNameBean [userList=" + userList + "]";
	}
	
	
}
