package com.iteam.supernfc.bean;

import java.util.List;

public class SyResponse {

	public boolean status;
	public List<SyInfo> assetLimitList;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<SyInfo> getAssetLimitList() {
		return assetLimitList;
	}
	public void setAssetLimitList(List<SyInfo> assetLimitList) {
		this.assetLimitList = assetLimitList;
	}
	@Override
	public String toString() {
		return "SyResponse [status=" + status + ", assetLimitList="
				+ assetLimitList + "]";
	}
	
	
	
}
