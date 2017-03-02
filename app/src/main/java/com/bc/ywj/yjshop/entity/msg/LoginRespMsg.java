package com.bc.ywj.yjshop.entity.msg;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class LoginRespMsg<T> extends BaseRespMsg {
    private String token;
    private T data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
