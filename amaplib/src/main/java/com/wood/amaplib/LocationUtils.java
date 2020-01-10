package com.wood.amaplib;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wood.amaplib.bean.LocationInfo;
import com.wood.amaplib.constants.LocationType;
import com.wood.amaplib.interfaces.OnLocationCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description: 高德地图定位功能
 * @Author: damanz
 * @CreateDate: 2020/1/7 9:39
 * @Version: 1.0
 */
public class LocationUtils {

    private static LocationUtils mLocationUtils = null;
    private AMapLocationClient mlocationClient;

    public static LocationUtils getInstance() {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        return mLocationUtils;
    }

    private LocationUtils() {
    }

    /**
     * @method requsetLocation
     * @param context
     * @param callback
     * @description 请求定位（连续定位 2秒定位一次）
     * @date: 2020/1/7 10:02
     * @author: damanz
     */
    public void requsetLocation(Context context, @NonNull OnLocationCallback callback) {
        //初始化定位
        mlocationClient = getAMapLocationClient(context);
        //初始化定位参数
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    LocationInfo info = new LocationInfo(aMapLocation);
                    if (info.getErrorCode() == 0) {
                        callback.onLocationChanged(info);
                    }else {
                        callback.onLocationError(info);
                    }
                }
            }
        });
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setInterval(2000);
        mlocationClient.setLocationOption(locationOption);
        mlocationClient.startLocation();//启动定位
    }

    private AMapLocationClient getAMapLocationClient(Context context) {
        destroyAMapLocationClient();
        return new AMapLocationClient(context);
    }

    public void destroyAMapLocationClient() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

}
