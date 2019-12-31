package com.wood.databaselib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wood.databaselib.entity.DaoMaster;
import com.wood.databaselib.interfaces.OnDbUpgradeListener;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

class UpgradeHelper extends DaoMaster.OpenHelper {
    private OnDbUpgradeListener mListener;

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");
        if (mListener != null) {
            Class<? extends AbstractDao<?, ?>>[] classes = mListener.upgradeDbClass();
            if (classes != null) {
                MigrationHelper.getInstance().migrate(db, classes);
            } else {
                super.onUpgrade(db, oldVersion, newVersion);
            }
        } else {
            super.onUpgrade(db, oldVersion, newVersion);
        }
    }

    public void setOnDbUpgradeListener(OnDbUpgradeListener mListener) {
        this.mListener = mListener;
    }
}
