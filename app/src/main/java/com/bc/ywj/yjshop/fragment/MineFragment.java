package com.bc.ywj.yjshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.activity.LoginActivity;
import com.bc.ywj.yjshop.application.MyApplication;
import com.bc.ywj.yjshop.entity.User;
import com.bc.ywj.yjshop.http.Contants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.img_head)
    CircleImageView mHeadCIV;
    @BindView(R.id.txt_username)
    TextView mUserNameTV;
    @BindView(R.id.btn_logout)
    Button mLogoutBtn;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void init() {
        showUser();
    }

    @OnClick({R.id.txt_username})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.txt_username:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, Contants.REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void showUser() {
        User user = MyApplication.getInstance().getUser();
        if (user == null) {
            mLogoutBtn.setVisibility(View.GONE);
            mHeadCIV.setImageResource(R.drawable.default_head);
            mUserNameTV.setText(R.string.toLogin);
        } else {
            mLogoutBtn.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(user.getLogo_url())) {

                Picasso.with(getActivity()).load(Contants.API.BASE_URL +
                        user.getLogo_url()).into(mHeadCIV);
            }
            mUserNameTV.setText(user.getUsername());
        }

    }
}
