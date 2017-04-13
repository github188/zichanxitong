package com.szcomtop.meal.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.szcomtop.meal.Context.DatabaseContext;
import com.szcomtop.meal.model.AssetInfo;
import com.szcomtop.meal.model.UserInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuming on 16/11/13.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "zcxt.db";
    public static final String DATABASE_PATH = Environment
            .getExternalStorageDirectory() + "/zcxt/zcxt.db";



    private Map<String, Dao> daos = new HashMap<String, Dao>();


    private static DatabaseHelper instance;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH, null, 1);
    }
    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }
    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READONLY);
    }


//    private DatabaseHelper(Context context)
//    {
//        super(new DatabaseContext(context , "/FEZcxt"), TABLE_NAME, null, 1);
//
//    }


//    @Override
//    public synchronized SQLiteDatabase getWritableDatabase() {
//        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
//                SQLiteDatabase.OPEN_READWRITE);
//    }
//
//    public synchronized SQLiteDatabase getReadableDatabase() {
//        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
//                SQLiteDatabase.OPEN_READONLY);
//    }



//    private DatabaseHelper(Context context)
//    {
//        super(context, DB_NAME, null, 1);
////        getWritableDatabase();
//
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try
        {
            TableUtils.createTable(connectionSource, AssetInfo.class);
            TableUtils.createTable(connectionSource, UserInfo.class);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }


    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();

        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }


}
