package com.szcomtop.meal.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.szcomtop.meal.common.Consts;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.UserInfo;
import com.szcomtop.meal.utils.PreferencesUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by wuming on 16/11/13.
 */
public class AssetInfoDao {


    private Context context;
    private DatabaseHelper helper;
    private Dao assetInfoOpe;


    public AssetInfoDao(Context context) {

        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            assetInfoOpe = helper.getDao(AssetInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void updateList(final List<AssetInfo> assetInfos) {

        try {
            assetInfoOpe.callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    Log.i("wuming", "updateList:" + Thread.currentThread().getName());

                    for (AssetInfo assetInfo : assetInfos) {

                        Log.i("wuming", assetInfo.toString());

                        assetInfoOpe.createOrUpdate(assetInfo);

                    }

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void deleteList(final List<AssetInfo> assetInfos) {

        try {
            assetInfoOpe.callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    Log.i("wuming", "deleteList:" + Thread.currentThread().getName());


                    for (AssetInfo assetInfo : assetInfos) {


                        assetInfoOpe.delete(assetInfo);

                    }

                    PreferencesUtils.putString(context, Consts.UPDATE_TIME, System.currentTimeMillis() / 1000 + "");

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void refreshList(final List<AssetInfo> assetInfos) {

        try {
            assetInfoOpe.callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {


                    for (AssetInfo assetInfo : assetInfos) {

                        assetInfoOpe.refresh(assetInfo);

                    }

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 增加一个资产
     *
     * @param assetInfo
     */
    public void add(AssetInfo assetInfo) {
        try {
//            assetInfoOpe.create(assetInfo);
            assetInfoOpe.createOrUpdate(assetInfo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<AssetInfo> queryAll() {


        List<AssetInfo> assetInfos = null;
        try {
            assetInfos = assetInfoOpe.queryForAll();


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return assetInfos;
    }


    /**
     * 资产查找
     * @param name 资产名称
     * @param offset  = page*count
     * @return
     */
    public List<AssetInfo> queryStoresByName(String name, long offset) {
        try {

            QueryBuilder pE = this.assetInfoOpe.queryBuilder();
            pE.offset(Long.valueOf(offset)).limit(Long.valueOf(10L));
            Where where = pE.where();
            where.like("name", "%" + name + "%");
            PreparedQuery pq = pE.prepare();
            Log.e("queryStoresByName", pq.getStatement());
            return this.assetInfoOpe.query(pq);
        } catch (SQLException var3) {

            var3.printStackTrace();
            Log.e("queryStoresByName", var3.getMessage());
            return new ArrayList(0);
        }
    }

}
