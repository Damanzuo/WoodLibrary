package com.wood.databaselib;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.wood.databaselib.constants.DataBaseConst;
import com.wood.databaselib.deserializer.AreaDeserializer;
import com.wood.databaselib.deserializer.CityDeserializer;
import com.wood.databaselib.deserializer.ProvinceDeserializer;
import com.wood.databaselib.deserializer.StreetDeserializer;
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

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author zdm
 * @version 1.0
 * @date 2019/12/07 10:20
 * 仅允许查询，不允许修改
 * <p>
 * 数据插入需要8S时间
 */
public class ChinaCityHelper {
    private static final String TAG = "ChinaCityHelper";
    private Context mContext;
    private static ChinaCityHelper mCityUtils;
    private final String DB_NAME = "china_city.db";
    private DaoSession mReadDaoSession;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private Gson mGson;
    private SPUtils mSpUtils;

    public static ChinaCityHelper getInstance() {
        if (mCityUtils == null) {
            mCityUtils = new ChinaCityHelper();
        }
        return mCityUtils;
    }

    private ChinaCityHelper() {
    }

    public void init(Context context) {
        init(context, false);
    }

    public void init(Context context, boolean initStreet) {
        mContext = context;
        mSpUtils = SPUtils.getInstance("database_config");
        mDevOpenHelper = new DaoMaster.DevOpenHelper(
                mContext, DB_NAME, null);
        if (!isInitDb() || initStreet != isInitStreetDb()) {
            initDb(initStreet);
        } else {
            mContext.sendBroadcast(new Intent(DataBaseConst.ACTION_CITY_DATA_SUCCESS));
        }
    }

    public boolean isInitDb() {
        return mSpUtils.getBoolean("insertDb", false);
    }

    private boolean isInitStreetDb() {
        return mSpUtils.getBoolean("insertStreetDb", false);
    }

    private void initDb(boolean initStreet) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                DaoMaster daoMaster = new DaoMaster(mDevOpenHelper.getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();

                LogUtils.d("开始解析数据：" + System.currentTimeMillis());
                AsyncSession asyncSession = daoSession.startAsyncSession();
                asyncSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!isInitDb()) {
                                LogUtils.d("插入省数据：");
                                List<Province> provinces = getProvinceData();
                                daoSession.getProvinceDao().insertInTx(provinces);
                                LogUtils.d("插入市数据：");
                                List<City> cityData = getCityData();
                                daoSession.getCityDao().insertInTx(cityData);
                                LogUtils.d("插入区数据：");
                                List<Area> areaData = getAreaData();
                                daoSession.getAreaDao().insertInTx(areaData);
                            }
                            if (initStreet) {
                                LogUtils.d("插入街道数据：");
                                List<Street> streetData = getStreetData();
                                daoSession.getStreetDao().insertInTx(streetData);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            mSpUtils.put("insertDb", false);
                            mSpUtils.put("insertStreetDb", false);
                        }
                    }
                });

                asyncSession.setListenerMainThread(new AsyncOperationListener() {
                    @Override
                    public void onAsyncOperationCompleted(AsyncOperation operation) {
                        mSpUtils.put("insertDb", true);
                        mSpUtils.put("insertStreetDb", initStreet);
                        LogUtils.d("完成解析数据：" + System.currentTimeMillis());
                        mContext.sendBroadcast(new Intent(DataBaseConst.ACTION_CITY_DATA_SUCCESS));
                    }
                });

            }
        }).start();
    }

    private DaoSession getReadDaoSession() {
        if (mReadDaoSession == null) {
            DaoMaster daoMaster = new DaoMaster(mDevOpenHelper.getReadableDatabase());
            mReadDaoSession = daoMaster.newSession();
        }
        return mReadDaoSession;
    }

    private List<Province> getProvinceData() throws IOException {
        InputStream open = mContext.getAssets().open("provinces.json");
        return createGson().fromJson(new InputStreamReader(open), new TypeToken<List<Province>>() {
        }.getType());
    }

    private List<City> getCityData() throws IOException {
        InputStream open = mContext.getAssets().open("city.json");
        return createGson().fromJson(new InputStreamReader(open), new TypeToken<List<City>>() {
        }.getType());
    }

    private List<Area> getAreaData() throws IOException {
        InputStream open = mContext.getAssets().open("area.json");
        return createGson().fromJson(new InputStreamReader(open), new TypeToken<List<Area>>() {
        }.getType());
    }

    private List<Street> getStreetData() throws IOException {
        InputStream open = mContext.getAssets().open("street.json");
        return createGson().fromJson(new InputStreamReader(open), new TypeToken<List<Street>>() {
        }.getType());
    }

    private Gson createGson() {
        if (mGson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            builder.registerTypeAdapter(Province.class, new ProvinceDeserializer());
            builder.registerTypeAdapter(City.class, new CityDeserializer());
            builder.registerTypeAdapter(Area.class, new AreaDeserializer());
            builder.registerTypeAdapter(Street.class, new StreetDeserializer());
            builder.registerTypeAdapter(String.class, new TypeAdapter<String>() {

                @Override
                public void write(JsonWriter out, String value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }
                    out.value(value);
                }

                @Override
                public String read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return "";
                    }
                    return in.nextString();
                }
            });
            mGson = builder.create();
        }
        return mGson;
    }

    /**
     * 按照 ProvinceCode,sort排序返回
     */
    public List<Province> loadAllProvince() {
        return getReadDaoSession().getProvinceDao().queryBuilder()
                .orderAsc(ProvinceDao.Properties.ProvinceCode)
                .orderAsc(ProvinceDao.Properties.Sort)
                .list();
    }

    public List<City> loadAllCity() {
        return getReadDaoSession().getCityDao().queryBuilder()
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
        return getReadDaoSession().getAreaDao().queryBuilder()
                .where(AreaDao.Properties.CityCode.eq(cityCode))
                .orderAsc(AreaDao.Properties.Sort)
                .list();
    }

    public List<Area> loadAllArea() {
        return getReadDaoSession().getAreaDao().queryBuilder()
                .list();
    }

    /**
     * 通过区、县、镇编号 查询区、县、镇的街道信息
     *
     * @param areaCode 区、县、镇编号
     * @return 街道信息
     */
    public List<Street> loadAllStreetFromArea(int areaCode) {
        return getReadDaoSession().getStreetDao().queryBuilder()
                .where(StreetDao.Properties.AreaCode.eq(areaCode))
                .orderAsc(StreetDao.Properties.Sort)
                .list();
    }

    public List<Street> loadAllStreet() {
        return getReadDaoSession().getStreetDao().queryBuilder()
                .list();
    }

    public List<City> loadAllCityFromProvince(int provinceCode) {
        return getReadDaoSession().getCityDao().queryBuilder()
                .where(CityDao.Properties.ProvinceCode.eq(provinceCode))
                .orderAsc(CityDao.Properties.Sort)
                .list();
    }

    public List<City> loadAllCityOrderByPinYin() {
        return getReadDaoSession().getCityDao().queryBuilder()
                .orderAsc(CityDao.Properties.PinYin)
                .list();
    }

    public List<City> loadCityForQueryKey(String queryKey) {
        return getReadDaoSession().getCityDao().queryBuilder()
                .where(CityDao.Properties.CityName.like("%" + queryKey + "%"))
                .orderAsc(CityDao.Properties.PinYin)
                .list();
    }
}
