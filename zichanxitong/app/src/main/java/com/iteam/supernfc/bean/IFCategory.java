package com.iteam.supernfc.bean;

public class IFCategory {

    public String id;
    public boolean isNewRecord;
    public String name;
    public int sort;
    public String module;
    public String isMenu;
    public String inList;
    public String showModes;
    public String allowComment;
    public String isAudit;
    public String ids;
    public String parentId;
    
    public boolean root;

	@Override
	public String toString() {
		return "IFCategory [id=" + id + ", isNewRecord=" + isNewRecord
				+ ", name=" + name + ", sort=" + sort + ", module=" + module
				+ ", isMenu=" + isMenu + ", inList=" + inList + ", showModes="
				+ showModes + ", allowComment=" + allowComment + ", isAudit="
				+ isAudit + ", ids=" + ids + ", parentId=" + parentId
				+ ", root=" + root + "]";
	}
    
    
    
	
}
