package com.wood.retrofilib;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.util.Log;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";

    @Override
    public void onNext(T t) {
        if (t == null) {
            onError(HttpCode.ERROR_EMPTY_OBJ, getErrorMessage(HttpCode.ERROR_EMPTY_OBJ));
        } else {
            onSuccess(t);
        }
    }

    /**
     * 外部想要处理异常时(比如分页加载失败，需要隐藏加载中效果)，可以重写该方法
     */
    public void onError(int errorCode, String message) {
    }

    public abstract void onSuccess(T t);

    /**
     * 不显示服务器返回错误信息(部分接口返回不规范)
     */
    public boolean isShowErrorToast() {
        return true;
    }

    @Override
    public void onError(Throwable e) {
        int errorCode = -1;
        String errMsg = "";
        //自定义异常
        if (e instanceof CustomHttpException) {
            CustomHttpException exception = (CustomHttpException) e;
            errorCode = exception.getErrorCode();
            errMsg = exception.getMessage();
            handleErrorCode(errorCode);
        } else if (e instanceof NullPointerException) { // RxJava2 发送值为null时，不执行 onNext，直接走 onError
            errorCode = HttpCode.ERROR_EMPTY_OBJ;
            errMsg = getErrorMessage(HttpCode.ERROR_EMPTY_OBJ);
        } else if (e instanceof SocketTimeoutException) {
            errorCode = HttpCode.ERROR_TIMEOUT;
            errMsg = getErrorMessage(HttpCode.ERROR_TIMEOUT);
        } else if (e instanceof NetworkErrorException) {
            errorCode = HttpCode.ERROR_NETWORK;
            errMsg = getErrorMessage(HttpCode.ERROR_NETWORK);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errMsg = httpException.getMessage();
            int httpErrorCode = httpException.code();
            Log.d(TAG, "Http request error:" + "message=" + errMsg + " :::: " + "httpErrorCode=" + httpErrorCode);
        } else {
            errMsg = e != null ? e.getMessage() : getErrorMessage(HttpCode.ERROR_UNKNOWN);
        }
        onError(errorCode, errMsg);
    }

    /**
     * 处理数据异常code
     *
     * @param errorCode
     */
    protected void handleErrorCode(int errorCode) {
        if (errorCode == HttpCode.ERROR_ALREADY_REGISTER) {
            //已注册处理逻辑
        } else if (errorCode == HttpCode.ERROR_LOGIN_EXPIRED) {
            //登录超时处理逻辑
        }
    }

    private String getErrorMessage(int errorCode) {
        String message = HttpCode.ERRORS.get(errorCode);
        if (TextUtils.isEmpty(message)) {
            message = HttpCode.ERRORS.get(HttpCode.ERROR_UNKNOWN);
        }
        return message;
    }
}
