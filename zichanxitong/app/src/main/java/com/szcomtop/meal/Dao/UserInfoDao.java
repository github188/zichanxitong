package com.szcomtop.meal.Dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.UserInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by wuming on 16/11/13.
 */
public class UserInfoDao {


    private Context context;
    private DatabaseHelper helper;
    private Dao userInfoOpe;


    public UserInfoDao(Context context){

        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            userInfoOpe = helper.getDao(UserInfo.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    /**
     * 增加一个用户
     * @param userInfo
     */
    public void add(UserInfo userInfo)
    {
        try
        {
            userInfoOpe.create(userInfo);


        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void addList(final List<UserInfo> userInfos){

        try {
            userInfoOpe.callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {

                    for (UserInfo userInfo : userInfos) {
                        userInfoOpe.create(userInfo);
                    }

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  void deleteAll(){

        try {
            userInfoOpe.executeRaw("DELETE  FROM tb_user");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean confirm(String account,String encodePsw){


        try {
            List query = userInfoOpe.queryBuilder().where().eq("user_name", account)
                    .and().eq("pass_word", encodePsw).query();
            if (query  == null || query.size() == 0){
                return  false ;
            }else {

                return true ;
            }
        } catch (SQLException e) {


        }

        return  false ;

    }


    public List<UserInfo> queryAll(){


        List<UserInfo>  userInfos = null;
        try
        {
            userInfos = userInfoOpe.queryForAll();


        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        return  userInfos ;



    }





}
