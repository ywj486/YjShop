package com.bc.ywj.yjshop.entity.msg;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class BaseRespMsg implements Serializable {
    private final static int STATUS_SUCCESS = 1;
    private final static int STATUS_ERROR = 1;
    private final static String MSG_SUCCESS = "success";


    protected int status=STATUS_SUCCESS;
    protected String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
