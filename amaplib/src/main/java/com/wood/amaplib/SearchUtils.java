package com.wood.amaplib;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.wood.amaplib.interfaces.OnPoiSearchCallback;

import java.util.ArrayList;

/**
 * @Description: java类作用描述
 * @Author: damanz
 * @CreateDate: 2020/1/8 10:27
 * @Version: 1.0
 */
public class SearchUtils {
    private static final String TAG = "SearchUtils";
    private static SearchUtils mSearchUtils = null;

    public static SearchUtils getInstance() {
        if (mSearchUtils == null) {
            mSearchUtils = new SearchUtils();
        }
        return mSearchUtils;
    }

    private SearchUtils() {
    }

    /**
     *
     * @param querykey  表示搜索字符串，
     * @param pioType 表示POI搜索类型
     * @param cityCode 表示POI搜索区域 可以是城市编码也可以是城市名称
     * @param pageSize  每页最多返回多少条poiitem
     * @param pageNum 查询页码
     */
    public void search(Context context, String querykey, String pioType, String cityCode, int pageSize, int pageNum, OnPoiSearchCallback callback) {
        PoiSearch.Query query = new PoiSearch.Query(querykey, pioType, cityCode);
        query.setPageSize(pageSize);
        query.setPageNum(pageNum);
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int code) {
                LogUtils.d("code: "+code);
                ArrayList<PoiItem> pois = poiResult.getPois();
                LogUtils.d(pois);
                if(code== 1000){
                    callback.onPoiSearched();
                }else {
                    callback.ononPoiSearchFail();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int code) {
                LogUtils.d("code: "+code);
                LogUtils.d(poiItem);
            }
        });
        poiSearch.searchPOIAsyn();
    }
}
