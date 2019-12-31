package com.wood.retrofilib;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class HttpData {
    private int result;
    @SerializedName(value = "msg", alternate = {"desc", "des"})
    private String msg;
    private JsonObject data;
}
