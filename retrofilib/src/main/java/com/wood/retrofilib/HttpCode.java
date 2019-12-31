package com.wood.retrofilib;

import android.util.SparseArray;

public class HttpCode {
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_SUCCESS = 0;
    // 1000~1099 自定义错误码
    public static final int ERROR_NETWORK = 1000;
    public static final int ERROR_TIMEOUT = 1001;
    public static final int ERROR_SERVER_EXCEPTION = 1002;
    public static final int ERROR_EMPTY_OBJ = 1011;
    public static final int ERROR_ALREADY_REGISTER = 100001; // 已经注册过
    public static final int ERROR_LOGIN_EXPIRED = 100002; // 登录cookie超时,需要重新登录
    public static final SparseArray<String> ERRORS = new SparseArray<>();

    static {
        ERRORS.append(ERROR_UNKNOWN, "未知错误");
        ERRORS.append(ERROR_SUCCESS, "请求成功");
        ERRORS.append(ERROR_NETWORK, "网络错误");
        ERRORS.append(ERROR_TIMEOUT, "连接超时");
        ERRORS.append(ERROR_SERVER_EXCEPTION, "服务器异常");
        ERRORS.append(ERROR_ALREADY_REGISTER, "您的手机号已经注册过i云保帐号");
        ERRORS.append(ERROR_LOGIN_EXPIRED, "登录超时,需要重新登录");
        ERRORS.append(ERROR_EMPTY_OBJ, "返回对象为空!");
    }
}
