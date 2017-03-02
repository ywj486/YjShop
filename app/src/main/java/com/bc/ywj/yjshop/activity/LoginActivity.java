package com.bc.ywj.yjshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.application.MyApplication;
import com.bc.ywj.yjshop.entity.User;
import com.bc.ywj.yjshop.entity.msg.LoginRespMsg;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.http.OkHttpHelper;
import com.bc.ywj.yjshop.http.SpotsCallBack;
import com.bc.ywj.yjshop.utils.MD5Utils;
import com.bc.ywj.yjshop.utils.ToastUtils;
import com.bc.ywj.yjshop.wight.ClearEditText;
import com.bc.ywj.yjshop.wight.ShopToolbar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    ShopToolbar mToolBar;
    @BindView(R.id.login_phone_cet)
    ClearEditText mEtxtPhone;
    @BindView(R.id.login_pwd_cet)
    ClearEditText mEtxtPwd;

    private OkHttpHelper okHttpHelper
            = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        mToolBar.setLeftButtonClickLinstener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.login_btn, R.id.login_reg_tv})
    public void loginOrReg(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String phone = mEtxtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show(this, "请输入手机号码");
                    return;
                }
                String pwd = mEtxtPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.show(this, "请输入密码");
                    return;
                }
                Map<String, Object> params = new HashMap<>(2);
                params.put("phone", phone);
                params.put("password", MD5Utils.ecoder(pwd));
                okHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(this) {


                    @Override
                    public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {
                        MyApplication application = MyApplication.getInstance();
                        application.putUser(userLoginRespMsg.getData(),
                                userLoginRespMsg.getToken());
                        setResult(RESULT_OK);
                        finish();

                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
                break;
            case R.id.login_reg_tv:

                break;
        }
    }
}
