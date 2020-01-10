package com.wood.amaplib.interfaces;

import com.wood.amaplib.bean.LocationInfo;

/**
 * @Description: 定位回调
 * @Author: damanz
 * @CreateDate: 2020/1/7 9:57
 * @Version: 1.0
 */
public interface OnLocationCallback {
    default void onLocationChanged(LocationInfo info) {
    }

    default void onLocationError(LocationInfo info) {

    }
}
