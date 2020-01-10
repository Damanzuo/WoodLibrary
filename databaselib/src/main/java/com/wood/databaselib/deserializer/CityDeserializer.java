package com.wood.databaselib.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wood.commonlib.utils.PinYinUtils;
import com.wood.databaselib.entity.City;
import com.wood.databaselib.entity.Province;

import java.lang.reflect.Type;

/**
 * @Description: 城市信息解析
 * @Author: damanz
 * @CreateDate: 2020/1/10 13:28
 * @Version: 1.0
 */
public class CityDeserializer implements JsonDeserializer<City> {

    @Override
    public City deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = (JsonObject) json;
        String cityName = obj.get("cityName").getAsString();
        String lat = obj.get("lat").getAsString();
        String lng = obj.get("lng").getAsString();
        int cityCode = obj.get("cityCode").getAsInt();
        int provinceCode = obj.get("provinceCode").getAsInt();
        String sort = obj.get("sort").getAsString();
        City city = new City();
        city.setLat(lat);
        city.setLng(lng);
        city.setCityCode(cityCode);
        city.setSort(sort);
        city.setCityName(cityName);
        city.setProvinceCode(provinceCode);
        String shortName;
        if ((cityName.endsWith("市") || cityName.endsWith("县")) && cityName.length() > 2) {
            shortName = cityName.substring(0, cityName.length() - 1);
        } else {
            shortName = cityName;
        }
        city.setShortName(shortName);
        city.setPinYin(PinYinUtils.getInstance().getStringPinYin(shortName));
        return city;
    }
}
