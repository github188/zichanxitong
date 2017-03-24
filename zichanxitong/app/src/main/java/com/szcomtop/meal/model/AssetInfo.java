package com.szcomtop.meal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by wuming on 16/11/13.
 */

@DatabaseTable(tableName = "tb_asset")
public class AssetInfo   implements Serializable{

    @DatabaseField(columnName = "id" )
    public String id;  //客户端id

    @DatabaseField(columnName = "name")
    public String name; //

    @DatabaseField(columnName = "rfid_code",id = true)
    public String rfid_code; //


    @DatabaseField(columnName = "brand_name")
    public String brand_name; //

    @DatabaseField(columnName = "desc")
    public String desc; //

    @DatabaseField(columnName = "asset_attr")
    public String asset_attr; //


    @DatabaseField(columnName = "asset_cat_id")
    public int asset_cat_id; //

    @DatabaseField(columnName = "asset_cat_name")
    public String cat_name ;

    @DatabaseField(columnName = "asset_brand_id")
    public int asset_brand_id; //


    public boolean isSelected ;

    public  String  status = "1"  ;

    public String msg ;


    public AssetInfo(){};

    public AssetInfo(String id, String name, String rfid_code, String brand_name) {
        this.id = id;
        this.name = name;
        this.rfid_code = rfid_code;
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "AssetInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rfid_code='" + rfid_code + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", desc='" + desc + '\'' +
                ", asset_attr='" + asset_attr + '\'' +
                ", asset_cat_id=" + asset_cat_id +
                ", asset_brand_id=" + asset_brand_id +
                ", isSelected=" + isSelected +
                '}';
    }
}
