package com.wood.amaplib.constants;

import lombok.Data;

/**
 * @Description: 高德定位类型  参考：https://lbs.amap.com/api/android-location-sdk/guide/utilities/location-type
 * @Author: damanz
 * @CreateDate: 2020/1/7 17:21
 * @Version: 1.0
 */

public enum LocationType {
    LOCATION_FAIL(0, "定位失败", "通过AMapLocation.getErrorCode()方法获取错误码，并参考错误码对照表进行问题排查"),
    LOCATION_GPS(1, "GPS定位结果", "通过设备GPS定位模块返回的定位结果，精度较高，在10米－100米左右"),
    LOCATION_BEFORE(2, "前次定位结果", "网络定位请求低于1秒、或两次定位之间设备位置变化非常小时返回，设备位移通过传感器感知"),
    LOCATION_CACHE(4, "缓存定位结果", "返回一段时间前设备在同样的位置缓存下来的网络定位结果"),
    LOCATION_WIFI(5, "Wifi定位结果", "属于网络定位，定位精度相对基站定位会更好，定位精度较高，在5米－200米之间"),
    LOCATION_BASE_STATION(6, "基站定位结果", "纯粹依赖移动、联通、电信等移动网络定位，定位精度在500米-5000米之间"),
    LOCATION_OFFLINE(8, "离线定位结果", ""),
    LOCATION_LAST(9, "最后位置缓存", ""),
    ;
    private int code;
    private String des;
    private String describe;

    LocationType(int code, String des, String describe) {
        this.code = code;
        this.des = des;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public String getDescribe() {
        return describe;
    }

    public static LocationType getLocationType(int code) {
        for (LocationType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return LOCATION_FAIL;
    }
}
