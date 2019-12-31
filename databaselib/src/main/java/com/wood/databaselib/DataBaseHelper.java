package com.wood.databaselib;

import android.content.Context;

import com.wood.databaselib.entity.DaoMaster;
import com.wood.databaselib.entity.DaoSession;
import com.wood.databaselib.interfaces.OnDbUpgradeListener;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.async.AsyncSession;

public class DataBaseHelper implements OnDbUpgradeListener {
    private static final String TAG = "DataBaseHelper";
    private static DataBaseHelper mDataBaseHelper;
    private Context mContext;
    private DaoSession mDaoSession;
    private UpgradeHelper mUpgradeHelper;

    private DataBaseHelper() {
        super();
    }

    public static DataBaseHelper getInstance() {
        if (null == mDataBaseHelper) {
            mDataBaseHelper = new DataBaseHelper();
        }
        return mDataBaseHelper;
    }

    public void init(Context context, String dbName) {
        mContext = context;
        mUpgradeHelper = new UpgradeHelper(mContext, dbName, null);
        mUpgradeHelper.setOnDbUpgradeListener(this);
        DaoMaster daoMaster = new DaoMaster(mUpgradeHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public Class<? extends AbstractDao<?, ?>>[] upgradeDbClass() {
        return null;
    }

    public AsyncSession getAsyncSession() {
        return mDaoSession.startAsyncSession();
    }
}
