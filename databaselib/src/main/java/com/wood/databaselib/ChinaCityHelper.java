package com.wood.databaselib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.wood.databaselib.entity.Area;
import com.wood.databaselib.entity.AreaDao;
import com.wood.databaselib.entity.City;
import com.wood.databaselib.entity.CityDao;
import com.wood.databaselib.entity.DaoMaster;
import com.wood.databaselib.entity.DaoSession;
import com.wood.databaselib.entity.Province;
import com.wood.databaselib.entity.ProvinceDao;
import com.wood.databaselib.entity.Street;
import com.wood.databaselib.entity.StreetDao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author zdm
 * @version 1.0
 * @Time 2019/12/07 10:20
 * 仅允许查询，不允许修改
 */
public class ChinaCityHelper {
    private static final String TAG = "ChinaCityHelper";
    private Context mContext;
    private static ChinaCityHelper mCityUtils;
    private final String DB_NAME = "china_city_20191230.db";
    private String DB_PATH;
    private DaoSession mDaoSession;

    public static ChinaCityHelper getInstance() {
        if (mCityUtils == null) {
            mCityUtils = new ChinaCityHelper();
        }
        return mCityUtils;
    }

    private ChinaCityHelper() {
    }

    public void init(Context context) {
        mContext = context;
        Log.e(TAG, "init: " + mContext.getFilesDir().getParent());
        DB_PATH = mContext.getFilesDir().getParent() + "/databases";
        Log.d(TAG, "init: " + DB_PATH);
        if (!hasDbFile()) {
            copyDbToDataDir();
        }
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(
                mContext, DB_NAME, new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {

                return null;
            }
        });
        DaoMaster daoMaster = new DaoMaster(openHelper.getReadableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    /**
     * 拷贝数据库到data/data/包名/databases
     */
    private void copyDbToDataDir() {
        Log.d(TAG, "copyDbToDataDir start: " + System.currentTimeMillis());
        try {
            BufferedInputStream is = new BufferedInputStream(mContext.getAssets().open(DB_NAME));
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(DB_PATH, DB_NAME)));
            int lenght;
            while ((lenght = is.read()) != -1) {
                os.write(lenght);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "copyDbToDataDir end : " + System.currentTimeMillis());
    }

    /**
     * 是否存在省市区的数据库
     *
     * @return true:存在  false:不存在
     */
    private boolean hasDbFile() {
        File f = new File(DB_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        File dbFile = new File(DB_PATH, DB_NAME);
        return dbFile.exists();
    }

    /**
     * 按照 ProvinceCode,sort排序返回
     */
    public List<Province> loadAllProvince() {
        return mDaoSession.getProvinceDao().queryBuilder()
                .orderAsc(ProvinceDao.Properties.ProvinceCode)
                .orderAsc(ProvinceDao.Properties.Sort).list();
    }

    public List<City> loadAllCity() {
        return mDaoSession.getCityDao().queryBuilder()
                .orderAsc(CityDao.Properties.ProvinceCode)
                .orderAsc(CityDao.Properties.CityCode)
                .orderAsc(CityDao.Properties.Sort)
                .list();
    }

    /**
     * 通过城市 查询城市所有的区、县、镇
     *
     * @param cityCode 城市编号
     * @return Area
     */
    public List<Area> loadAllAreaFromCity(int cityCode) {
        return mDaoSession.getAreaDao().queryBuilder()
                .where(AreaDao.Properties.CityCode.eq(cityCode))
                .orderAsc(AreaDao.Properties.Sort)
                .list();
    }

    /**
     * 通过区、县、镇编号 查询区、县、镇的街道信息
     *
     * @param areaCode 区、县、镇编号
     * @return 街道信息
     */
    public List<Street> loadAllStreetFromArea(int areaCode) {
        return mDaoSession.getStreetDao().queryBuilder()
                .where(StreetDao.Properties.AreaCode.eq(areaCode))
                .orderAsc(StreetDao.Properties.Sort)
                .list();
    }

    public List<City> loadAllCityFromProvince(int provinceCode) {
        return mDaoSession.getCityDao().queryBuilder()
                .where(CityDao.Properties.ProvinceCode.eq(provinceCode))
                .orderAsc(CityDao.Properties.Sort)
                .list();
    }

    public List<City> loadAllCityOrderByPinYin() {
        return mDaoSession.getCityDao().queryBuilder()
                .orderAsc(CityDao.Properties.PinYin)
                .list();
    }

    public List<City> loadCityForQueryKey(String queryKey) {
        return mDaoSession.getCityDao().queryBuilder()
                .where(CityDao.Properties.CityName.like("%" + queryKey + "%"))
                .orderAsc(CityDao.Properties.PinYin)
                .list();
    }
}
