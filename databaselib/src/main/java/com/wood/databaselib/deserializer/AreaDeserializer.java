package com.wood.databaselib.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wood.commonlib.utils.PinYinUtils;
import com.wood.databaselib.entity.Area;
import com.wood.databaselib.entity.City;

import java.lang.reflect.Type;

/**
 * @Description: 区、县、乡信息解析
 * @Author: damanz
 * @CreateDate: 2020/1/10 13:28
 * @Version: 1.0
 */
public class AreaDeserializer implements JsonDeserializer<Area> {

    @Override
    public Area deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = (JsonObject) json;
        String areaName = obj.get("areaName").getAsString();
        String lat = obj.get("lat").getAsString();
        String lng = obj.get("lng").getAsString();
        int areaCode = obj.get("areaCode").getAsInt();
        int cityCode = obj.get("cityCode").getAsInt();
        String sort = obj.get("sort").getAsString();
        Area area = new Area();
        area.setLat(lat);
        area.setLng(lng);
        area.setAreaCode(areaCode);
        area.setSort(sort);
        area.setAreaName(areaName);
        area.setShortName(areaName);
        area.setCityCode(cityCode);
        area.setPinYin(PinYinUtils.getInstance().getStringPinYin(areaName));
        return area;
    }
}
