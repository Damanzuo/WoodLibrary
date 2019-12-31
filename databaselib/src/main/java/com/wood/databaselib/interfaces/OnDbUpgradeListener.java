package com.wood.databaselib.interfaces;

import org.greenrobot.greendao.AbstractDao;

public interface OnDbUpgradeListener {
    Class<? extends AbstractDao<?, ?>>[] upgradeDbClass() ;
}
