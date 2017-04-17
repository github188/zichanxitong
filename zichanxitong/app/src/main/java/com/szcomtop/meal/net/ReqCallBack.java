package com.szcomtop.meal.net;

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
     void onReqSuccess(T result);

    /**
     * 响应失败
     */
     void onReqFailed(String errorMsg);
}