package com.szcomtop.meal.common;

import java.util.HashMap;

/**
 * Created by wuming on 16/2/28.
 */
public class OverAllStorage {

    private static OverAllStorage instance;
    private final HashMap<Object, Object> storageMap;

    private OverAllStorage(){

        storageMap = new HashMap<>();
//        storageMap.put(MyLocationListener.LATITUDE,0.0d);
//        storageMap.put(MyLocationListener.LONTITUDE,0.0d);
//        storageMap.put(MyLocationListener.ADDR,"");
        storageMap.put("userId","");
        storageMap.put("openId","");
        storageMap.put("userName","");

        storageMap.put("dayScope","0");


    }

    public static  synchronized OverAllStorage getInstance(){

        if (instance == null) {
            instance = new OverAllStorage();
        }
        return instance;
    }

    public void put(String key ,Object value){
        storageMap.put(key,value);
    }

    public Object get(String key){
       return storageMap.get(key);
    }



}
