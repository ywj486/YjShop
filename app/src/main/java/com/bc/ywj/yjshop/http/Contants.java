package com.bc.ywj.yjshop.http;

/**
 * Created by Administrator on 2017/2/17 0017.
 */
public class Contants {
    public static final String COMPAIGN_ID="campaign_id";
    public static final String WARE="ware";
    public static final String USER_JSON="user_json";
    public static final String TOKEN="token";
    public static final int REQUEST_CODE=0;

    public static class API {
       public static final String BASE_URL = "http://101.200.167.75:8080/phoenixshop/";
      //  public static final String BASE_URL = "http://192.168.155.123:8080/hlshop/";
        public static final String BANNER = BASE_URL + "banner/query";
        public static final String CAMPAIGN_HOME = BASE_URL + "campaign/recommend";
        public static final String WARES_HOT = BASE_URL + "wares/hot";
        public static final String CATEGORY_LIST = BASE_URL + "category/list";
        public static final String WARES_LIST = BASE_URL + "wares/list";
        public static final String CAMPAIGN_LIST = BASE_URL + "campaign/list";
        public static final String WARES_DETAIL = BASE_URL + "wares/detail.html";
        public static final String LOGIN = BASE_URL + "auth/login";

    }
}
