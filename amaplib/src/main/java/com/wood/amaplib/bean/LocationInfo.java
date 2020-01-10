package com.wood.amaplib.bean;

import com.amap.api.location.AMapLocation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Data;

/**
 * @Description: java类作用描述
 * @Author: damanz
 * @CreateDate: 2020/1/7 10:14
 * @Version: 1.0
 */
@Data
public class LocationInfo {
    private static final String TAG = "LocationInfo";
    // 纬度
    private double latitude;
    //经度
    private double longitude;
    //定位精度 单位:米
    private float accuracy;
    // 海拔
    private double altitude;
    //速度 单位：米/秒
    private float speed;
    //方向角
    private float bearing;
    //室内定位建筑物Id
    private String buildingId;
    //室内定位楼层
    private String floor;
    //地址描述
    private String address;
    //时间
    private String time;
    //    国家
    private String country;
    //省
    private String province;
    //城市
    private String city;
    // 城区
    private String district;
    //街道
    private String street;
    //街道门牌号
    private String streetNum;
    //城市编码
    private String cityCode;
    // 区域编码
    private String adCode;
    //当前位置POI名称
    private String poiName;
    //当前位置所处AOI名称
    private String aoiName;
    //设备当前 GPS 状态
    private int gpsAccuracyStatus;
    //定位来源
    private int locationType;
    // 定位信息描述
    private String locationDetail;
    //定位错误信息描述
    private String errorInfo;
    //定位错误码
    private int errorCode;


    public LocationInfo(AMapLocation aMapLocation) {
        this.locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
        this.latitude = aMapLocation.getLatitude();//获取纬度
        this.longitude = aMapLocation.getLongitude();//获取经度
        this.accuracy = aMapLocation.getAccuracy();//获取精度信息
        this.address = aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        this.country = aMapLocation.getCountry();//国家信息
        this.province = aMapLocation.getProvince();//省信息
        this.city = aMapLocation.getCity();//城市信息
        this.district = aMapLocation.getDistrict();//城区信息
        this.street = aMapLocation.getStreet();//街道信息
        this.streetNum = aMapLocation.getStreetNum();//街道门牌号信息
        this.cityCode = aMapLocation.getCityCode();//城市编码
        this.adCode = aMapLocation.getAdCode();//地区编码
        this.aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
        this.buildingId = aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
        this.floor = aMapLocation.getFloor();//获取当前室内定位的楼层
        this.gpsAccuracyStatus = aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
        //获取定位时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date date = new Date(aMapLocation.getTime());
        this.time = df.format(date);
        this.speed = aMapLocation.getSpeed();
        this.altitude = aMapLocation.getAltitude();
        this.bearing = aMapLocation.getBearing();
        this.poiName = aMapLocation.getPoiName();
        this.locationDetail = aMapLocation.getLocationDetail();
        this.errorCode = aMapLocation.getErrorCode();
        this.errorInfo = aMapLocation.getErrorInfo();
    }
}
