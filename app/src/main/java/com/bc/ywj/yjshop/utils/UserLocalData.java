package com.bc.ywj.yjshop.utils;

import android.content.Context;
import android.text.TextUtils;

import com.bc.ywj.yjshop.entity.User;
import com.bc.ywj.yjshop.http.Contants;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class UserLocalData {
    public static void putUser(Context context, User user){
        String user_json=GsonUtils.toJson(user);
        SharedPreferenceUtils.putString(context, Contants.USER_JSON,user_json);
    }

    public static void putToken(Context context,String token){
        SharedPreferenceUtils.putString(context, Contants.TOKEN,token);
    }
    public static User getUser(Context context){
        String user_json=SharedPreferenceUtils.getString(
                context,Contants.USER_JSON
        );
        if(!TextUtils.isEmpty(user_json)) {
            return GsonUtils.fromJson(user_json,User.class);
        }
        return null;
    }

    public static String getToken(Context context){
        return SharedPreferenceUtils.getString(context,Contants.TOKEN);
    }
    public static void clearUser(Context context){
        SharedPreferenceUtils.putString(context,Contants.USER_JSON,"");
    }
    public static void clearToken(Context context){
        SharedPreferenceUtils.putString(context,Contants.TOKEN,"");
    }


}
