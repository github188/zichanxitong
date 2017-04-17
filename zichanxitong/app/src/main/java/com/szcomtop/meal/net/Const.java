package com.szcomtop.meal.net;

/**
 * Created by wuming on 16/11/23.
 */
public class Const {


//    public static  final  String HOST_TEST = "asset.upbaiwg.com";



    public static  final  String LOGIN_SCERET = "khkjsdhfmncxbvur983287148sdjkhfkj";

    public static  final  String TOKEN_SCERET = "PqL80TJlCMb67yDN";



    public static  final  String LOGIN_URL = "/public/api/login/check";

    public static  final  String GET_USER_LIST_URL = "/public/api/user/get_user_list";

    public static  final  String REGISTE_ASSET_URL = "/public/api/asset/asset_register";


    public static  final  String ASSET_EXECUTE_URL = "/public/api/asset/execute_entry";
    public static  final  String ASSET_APPLY_OUT_URL = "/public/api/asset/apply_out";

    public static  final  String ASSET_UPDATE_URL = "/public/api/asset/download_assets_info";


    public static  final  String PANDIAN_NUM_HISTORY = "/public/api/asset/getHistoryInventoryNum";

    public static  final  String PANDIAN_NUM_NEW = "/public/api/asset/get_inventory_num";

    public static  final  String PANDIAN_URL= "/public/api/asset/assets_inventory";


    public static final String GET_ALL_DELETE_AASET = "/public/api/asset/getAllDelAsset";

    //以下为新接口  add by renjianhong  2017-4-6
    //  public static  final  String HOST_TEST = "xdwl.tunnel.qydev.com";
    public static  final  String HOST_TEST = "221.131.145.100";
    public static  final  String NEW_LOGIN_URL = "/service/handset/login";

    /**
    *  同步管理员用户  参数：officeId
    */
    public static  final  String SYNC_ACCOUNT = "/handset/synchro/account/";

    /**
     *  同步资产  参数：officeId、pageno
     */
    public static  final  String SYNC_ASSET = "/service/handset/synchro/asset/";

    /**
     *  同步资产批次  参数：officeId、pageno
     */
    public static  final  String SYNC_ASSET_BATCH = "/service/handset/synchro/assetbatch/";

    /**
     *  同步保管人  参数：officeId、pageno
     */
    public static  final  String SYNC_KEEPER = "/service/handset/synchro/keeper/";

    /**
     *  同步部门  参数：officeId
     */
    public static  final  String SYNC_DEPARTMENT = "/service/handset/synchro/department/";

    /**
     *  同步区域  参数：officeId
     */
    public static  final  String SYNC_WORKAREA = "/service/handset/synchro/workarea/";

    /**
     *  同步资产属性  参数：officeId
     */
    public static  final  String SYNC_ASSET_NATURE = "/service/handset/synchro/assetnature";

}
