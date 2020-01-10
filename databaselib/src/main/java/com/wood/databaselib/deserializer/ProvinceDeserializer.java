package com.wood.databaselib.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wood.commonlib.utils.PinYinUtils;
import com.wood.databaselib.entity.Province;

import java.lang.reflect.Type;

/**
 * @Description: 省信息解析
 * @Author: damanz
 * @CreateDate: 2020/1/10 13:28
 * @Version: 1.0
 */
public class ProvinceDeserializer implements JsonDeserializer<Province> {

    @Override
    public Province deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = (JsonObject) json;
        String provinceName = obj.get("provinceName").getAsString();
        String lat = obj.get("lat").getAsString();
        String lng = obj.get("lng").getAsString();
        int provinceCode = obj.get("provinceCode").getAsInt();
        String sort = obj.get("sort").getAsString();
        Province province = new Province();
        province.setLat(lat);
        province.setLng(lng);
        province.setProvinceCode(provinceCode);
        province.setSort(sort);
        province.setProvinceName(provinceName);
        String shortName;
        if (provinceName.endsWith("省")) {
            shortName = provinceName.substring(0, provinceName.length() - 1);
        } else if (provinceName.endsWith("自治区")||provinceName.endsWith("行政区")) {
            shortName = provinceName.substring(0, 3);
        } else {
            shortName = provinceName;
        }
        province.setShortName(shortName);
        province.setPinYin(PinYinUtils.getInstance().getStringPinYin(shortName));
        return province;
    }
}
