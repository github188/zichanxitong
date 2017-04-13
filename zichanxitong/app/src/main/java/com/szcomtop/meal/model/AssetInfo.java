package com.szcomtop.meal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

import static android.R.attr.data;
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


    @DatabaseField(columnName = "keeper_id")
    public String keeper_id; //

    @DatabaseField(columnName = "batch_no")
    public String batch_no; //

    @DatabaseField(columnName = "model_id")
    public String model_id; //


    @DatabaseField(columnName = "brand_id")
    public String brand_id; //

    @DatabaseField(columnName = "category_id")
    public String category_id ;

    @DatabaseField(columnName = "durable_years")
    public String durable_years; //

    @DatabaseField(columnName = "price")
    public String price; //

    @DatabaseField(columnName = "buy_date")
    public String buy_date; //

    @DatabaseField(columnName = "state")
    public String state; //

    @DatabaseField(columnName = "workarea_id")
    public String workarea_id; //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfid_code() {
        return rfid_code;
    }

    public void setRfid_code(String rfid_code) {
        this.rfid_code = rfid_code;
    }

    public String getKeeper_id() {
        return keeper_id;
    }

    public void setKeeper_id(String keeper_id) {
        this.keeper_id = keeper_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDurable_years() {
        return durable_years;
    }

    public void setDurable_years(String durable_years) {
        this.durable_years = durable_years;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkarea_id() {
        return workarea_id;
    }

    public void setWorkarea_id(String workarea_id) {
        this.workarea_id = workarea_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    @DatabaseField(columnName = "create_date")
    public String create_date; //

    public boolean isSelected ;

    public  String  status = "1"  ;

    public String msg ;

    public AssetInfo(){};

//    public AssetInfo(String id,String batch_no,String keeper_id,String model_id,String brand_id,
//                     String rfid_code,String category_id,String durable_years,String price,String buy_date,
//                     String name,String workarea_id,String create_date,String state){
//        this.id = id;
//        this.batch_no = batch_no;
//        this.keeper_id = keeper_id;
//        this.model_id = model_id;
//        this.brand_id = brand_id;
//        this.rfid_code = rfid_code;
//        this.category_id = category_id;
//        this.durable_years = durable_years;
//        this.price = price;
//        this.buy_date = buy_date;
//        this.name = name;
//        this.state = state;
//        this.workarea_id = workarea_id;
//        this.create_date = create_date;
//    };

    @Override
    public String toString() {
        return "AssetInfo{" +
                "keeper_id='" + keeper_id + '\'' +
                ", batch_no='" + batch_no + '\'' +
                ", model_id='" + model_id + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", rfid_code='" + rfid_code + '\'' +
                ", category_id='" + category_id + '\'' +
                ", durable_years=" + durable_years +'\'' +
                ", price=" + price +'\'' +
                ", buy_date=" + buy_date +'\'' +
                ", name=" + name +'\'' +
                ", id=" + id +'\'' +
                ", state=" + state +'\'' +
                ", workarea_id=" + workarea_id +'\'' +
                ", create_date=" + create_date +'\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
