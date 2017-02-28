package com.bc.ywj.yjshop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.HWAdapter;
import com.bc.ywj.yjshop.entity.PageResult;
import com.bc.ywj.yjshop.entity.Tab;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.fragment.CartFragment;
import com.bc.ywj.yjshop.fragment.CategoryFragment;
import com.bc.ywj.yjshop.fragment.HomeFragment;
import com.bc.ywj.yjshop.fragment.HotFragment;
import com.bc.ywj.yjshop.fragment.MineFragment;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.utils.PageUtils;
import com.bc.ywj.yjshop.wight.FragmentTabHost;
import com.bc.ywj.yjshop.wight.ShopToolbar;
import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WareListActivity extends AppCompatActivity
        implements PageUtils.OnPageListener {
    @BindView(R.id.ware_list_tl)
    TabLayout mTabLayout;
    @BindView(R.id.ware_list_summary_tv)
    TextView mSummaryTv;
    @BindView(R.id.ware_list_rv)
    RecyclerView mWaresRecylerView;
    @BindView(R.id.ware_list_mrl)
    MaterialRefreshLayout mRefreshLayout;

    private int orderBy = 0;
    private long campaignId = 0;
    private HWAdapter mWaresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);
        ButterKnife.bind(this);

        campaignId = getIntent().getLongExtra(
                Contants.COMPAIGN_ID, 0);

        initTab();
        getData();
    }

    private void initTab() {
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("默认");
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("价格");
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("销量");
        mTabLayout.addTab(tab);

    }

    private void getData() {
        PageUtils pageUtils = PageUtils.newBuilder()
                .setUrl(Contants.API.CAMPAIGN_LIST)
                .putParam("campaignId", campaignId)
                .putParam("orderBy", orderBy)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(this)
                .build(this, new TypeToken<PageResult<Wares>>() {
                }.getType());
        pageUtils.request();

    }

    @Override
    public void load(List datas, int totalPage, int totalCount) {

    }

    @Override
    public void refresh(List datas, int totalPage, int totalCount) {

    }

    @Override
    public void loadMore(List datas, int totalPage, int totalCount) {

    }
}
