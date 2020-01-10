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
public class Street {
    @Id(autoincrement = true)
    @Property(nameInDb = "street_ID")
    private Long id;
    @Property(nameInDb = "AREA_CODE")
    private int areaCode;
    @Property(nameInDb = "STREET_NAME")
    private String streetName;
    @Property(nameInDb = "STREET_CODE")
    private int streetCode;
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
    @Generated(hash = 875826336)
    public Street(Long id, int areaCode, String streetName, int streetCode,
            String shortName, String lng, String lat, String sort, String pinYin) {
        this.id = id;
        this.areaCode = areaCode;
        this.streetName = streetName;
        this.streetCode = streetCode;
        this.shortName = shortName;
        this.lng = lng;
        this.lat = lat;
        this.sort = sort;
        this.pinYin = pinYin;
    }
    @Generated(hash = 1146976529)
    public Street() {
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
    public String getStreetName() {
        return this.streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public int getStreetCode() {
        return this.streetCode;
    }
    public void setStreetCode(int streetCode) {
        this.streetCode = streetCode;
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
