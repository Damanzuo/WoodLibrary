package com.wood.amaplib.interfaces;

/**
 * @Description: java类作用描述
 * @Author: damanz
 * @CreateDate: 2020/1/8 14:15
 * @Version: 1.0
 */
public interface OnPoiSearchCallback {
    default void onPoiSearched() {
    }

    default void ononPoiSearchFail() {
    }
}
