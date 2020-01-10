package com.wood.databaselib.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author zdm
 * @version 1.0
 * @Time 2019/12/07 11:00
 */
@Entity
public class Area {
    @Id(autoincrement = true)
    @Property(nameInDb = "AREA_ID")
    private Long id;
    @Property(nameInDb = "AREA_CODE")
    private int areaCode;
    @Property(nameInDb = "AREA_NAME")
    private String areaName;
    @Property(nameInDb = "CITY_CODE")
    private int cityCode;
    /**
     * 简称
     */
    @Property(nameInDb = "SHORT_NAME")
    private String shortName;
    /**
     * 经度
     */
    @Property(nameInDb = "LNG")
    private String lng;
    /**
     * 纬度
     */
    @Property(nameInDb = "LAT")
    private String lat;
    /**
     * 排序
     */
    @Property(nameInDb = "SORT")
    private String sort;
    @Property(nameInDb = "PIN_YIN")
    private String pinYin;
    @Generated(hash = 903191458)
    public Area(Long id, int areaCode, String areaName, int cityCode,
            String shortName, String lng, String lat, String sort, String pinYin) {
        this.id = id;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.cityCode = cityCode;
        this.shortName = shortName;
        this.lng = lng;
        this.lat = lat;
        this.sort = sort;
        this.pinYin = pinYin;
    }
    @Generated(hash = 179626505)
    public Area() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAreaCode() {
        return this.areaCode;
    }
    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }
    public String getAreaName() {
        return this.areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public int getCityCode() {
        return this.cityCode;
    }
    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
    public String getShortName() {
        return this.shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getLng() {
        return this.lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getSort() {
        return this.sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getPinYin() {
        return this.pinYin;
    }
    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}
