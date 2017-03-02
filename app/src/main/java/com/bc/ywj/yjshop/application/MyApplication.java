package com.bc.ywj.yjshop.application;

import android.app.Application;

import com.bc.ywj.yjshop.entity.User;
import com.bc.ywj.yjshop.utils.UserLocalData;
import com.facebook.drawee.backends.pipeline.Fresco;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/2/20 0020.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    private User user;

    public static MyApplication getInstance(){
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        initUser();
        //基本使用
        Fresco.initialize(this);
        ShareSDK.initSDK(this);
    }

    private void initUser() {
        user= UserLocalData.getUser(this);
    }

    public User getUser(){
        return user;
    }
    public void putUser(User user,String token){
        this.user=user;
        UserLocalData.putUser(this,user);
        UserLocalData.putToken(this,token);
    }
    public void clearUser(){
        this.user=null;
        UserLocalData.clearUser(this);
        UserLocalData.clearToken(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ShareSDK.stopSDK(this);
    }
}
