package com.szcomtop.meal.net;

import android.util.Log;

import com.iteam.supernfc.UHFApplication;
import com.szcomtop.meal.activity.CommonOperateActivity;
import com.szcomtop.meal.utils.MD5Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuming on 16/11/23.
 */
public class RestApi {



    static {
        OkHttpUtils.getInstance().debug("wuming",true);
        OkHttpUtils.getInstance().setConnectTimeout(3000, TimeUnit.MILLISECONDS);
        OkHttpUtils.getInstance().setReadTimeout(3000, TimeUnit.MILLISECONDS);
        OkHttpUtils.getInstance().setWriteTimeout(3000, TimeUnit.MILLISECONDS);



    }


    /**
     * 登陆
     * @param account
     * @param password
     * @param callback
     */
    public static void login(String account, String password, Callback callback){

//        OkHttpUtils.post().url(UHFApplication.getHost()+Const.LOGIN_URL)
//                .addParams("account",account)
//                .addParams("password", MD5Util.getMD5(MD5Util.getMD5(password)+Const.LOGIN_SCERET))
//                .addParams("token", MD5Util.getMD5(account+Const.TOKEN_SCERET))
//                .build().execute(callback);

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.NEW_LOGIN_URL)
                .addParams("loginname",account)
                .addParams("password", password)
                .build().execute(callback);
        Log.e("RENRENREN","请求参数 url="+UHFApplication.getHost()+Const.NEW_LOGIN_URL+" account "+account+" password "+password);
    }

    public static void getUsers(Callback callback){

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.GET_USER_LIST_URL)

                .addParams("token", MD5Util.getMD5(Const.TOKEN_SCERET))
                .build().execute(callback);

    }


    /**
     * 资产注册
     * @param ids
     * @param operatorId
     * @param callback
     */
    public static  void assetRegiste(List<String> ids , String operatorId,String schoolId , Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.REGISTE_ASSET_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("schoolId",schoolId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .tag(CommonOperateActivity.TYPE_REGISTE)
                .build().execute(callback);


    }


    /**
     * 入库
     * @param ids
     * @param operatorId
     * @param callback
     */

    public  static void assetInStorage(List<String> ids , String operatorId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("type","1001")
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","GoStorage")
                .build().execute(callback);

    }


    /**
     * 出库
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetOutStorage(List<String> ids , String operatorId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_APPLY_OUT_URL)
//                .addParams("type","1001")
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
//                .addParams("cmd","OutStorage")
//                .tag(CommonOperateActivity.TYPE_OUT)
                .build().execute(callback);

    }


    /**
     * 领用
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetRecive(List<String> ids , String operatorId, String bowworId ,Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("type","1001")
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Received")
                .addParams("userId",bowworId)
                .tag(CommonOperateActivity.TYPE_OUT)
                .build().execute(callback);

    }


    /**
     * 调拨
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetTransfer(List<String> ids , String operatorId, String type ,Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("type",type)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Transfer")
                .addParams("reason","")
                .tag(CommonOperateActivity.TYPE_TRANSFER)
                .build().execute(callback);

    }

    /**
     * 借用
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetBorrow(List<String> ids , String operatorId,String borrowUser,String type , Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Borrow")
                .addParams("borrowUser",borrowUser)
                .tag(CommonOperateActivity.TYPE_BORROW)
                .build().execute(callback);

    }


    /**
     * 报废
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetScrap(List<String> ids , String operatorId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Scrap")
                .tag(CommonOperateActivity.TYPE_SCRAP)
                .build().execute(callback);

    }


    /**
     * 报损
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetDamaged(List<String> ids , String operatorId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Damaged")
                .tag(CommonOperateActivity.TYPE_DAMAGED)
                .build().execute(callback);

    }


    /**
     * 退还
     * @param ids
     * @param operatorId
     * @param callback
     */
    public  static void assetReturn(List<String> ids , String operatorId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_EXECUTE_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("cmd","Return")
                .tag(CommonOperateActivity.TYPE_RETURN)
                .build().execute(callback);

    }


    public  static void assetInventory(List<String> ids , String operatorId,String pandianNum,String officeId, Callback callback){

        if (ids == null || ids.size() == 0){
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i==0){
                stringBuilder.append(ids.get(i));
            }else {

                stringBuilder.append(",").append(ids.get(i));
            }
        }

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.PANDIAN_URL)
                .addParams("rfid_code",stringBuilder.toString())
                .addParams("operatorId",operatorId)
                .addParams("token", MD5Util.getMD5(stringBuilder.toString()+Const.TOKEN_SCERET))
                .addParams("inventory_num",pandianNum)
                .addParams("officeId",officeId)
                .build().execute(callback);

    }


    public  static  void updateAssetData(String updateTime,Callback callback){

        OkHttpUtils.post().url(UHFApplication.getHost()+Const.ASSET_UPDATE_URL)
                .addParams("update_time",updateTime)
                .addParams("token", MD5Util.getMD5(updateTime+Const.TOKEN_SCERET))
                .build().execute(callback);

    }


    public  static   void  getPandianHistory(String areaId,Callback callback){

        OkHttpUtils.post().url(UHFApplication.getHost() +Const.PANDIAN_NUM_HISTORY)
                .addParams("area_id",areaId)
                .addParams("token", MD5Util.getMD5(areaId+Const.TOKEN_SCERET))
                .build().execute(callback);

    }


    public  static   void  getPandianNew(String areaId,Callback callback){

        OkHttpUtils.post().url(UHFApplication.getHost() +Const.PANDIAN_NUM_NEW)
               // .addParams("area_id",areaId)
              //  .addParams("token", MD5Util.getMD5(areaId+Const.TOKEN_SCERET))
                .build().execute(callback);

    }

    /**
     * 获取删除的资产
     * @param time
     * @param callback
     */
    public   static   void  getAllDeleteAsset(String  time ,Callback callback){

        OkHttpUtils.post().url(UHFApplication.getHost() +Const.GET_ALL_DELETE_AASET)
                .addParams("timestamp",time)
                .addParams("token", MD5Util.getMD5(time+Const.TOKEN_SCERET))
                .build().execute(callback);

    }


}
