package com.wood.databaselib.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wood.commonlib.utils.PinYinUtils;
import com.wood.databaselib.entity.Area;
import com.wood.databaselib.entity.Street;

import java.lang.reflect.Type;

/**
 * @Description: 街道信息解析
 * @Author: damanz
 * @CreateDate: 2020/1/10 13:28
 * @Version: 1.0
 */
public class StreetDeserializer implements JsonDeserializer<Street> {

    @Override
    public Street deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = (JsonObject) json;
        String streetName = obj.get("streetName").getAsString();
        String lat = obj.get("lat").getAsString();
        String lng = obj.get("lng").getAsString();
        int streetCode = obj.get("streetCode").getAsInt();
        int areaCode = obj.get("areaCode").getAsInt();
        String sort = obj.get("sort").getAsString();
        Street street = new Street();
        street.setLat(lat);
        street.setLng(lng);
        street.setStreetCode(streetCode);
        street.setAreaCode(areaCode);
        street.setSort(sort);
        street.setStreetName(streetName);
        street.setShortName(streetName);
        street.setPinYin(PinYinUtils.getInstance().getStringPinYin(streetName));
        return street;
    }
}
