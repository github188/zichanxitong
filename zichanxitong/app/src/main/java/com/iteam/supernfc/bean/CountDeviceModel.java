package com.iteam.supernfc.bean;

public class CountDeviceModel {

	private String name;
	private int num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "CountDeviceModel [name=" + name + ", num=" + num + "]";
	}
	
	
	
}
