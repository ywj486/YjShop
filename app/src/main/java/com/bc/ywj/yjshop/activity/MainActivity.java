package com.bc.ywj.yjshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.entity.Tab;
import com.bc.ywj.yjshop.fragment.CartFragment;
import com.bc.ywj.yjshop.fragment.CategoryFragment;
import com.bc.ywj.yjshop.fragment.HomeFragment;
import com.bc.ywj.yjshop.fragment.HotFragment;
import com.bc.ywj.yjshop.fragment.MineFragment;
import com.bc.ywj.yjshop.wight.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabhost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_test);
        mInflater = LayoutInflater.from(this);
        initTab();
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class, R.drawable.selector_icon_home, R.string.home);
        Tab tab_hot = new Tab(HotFragment.class, R.drawable.selector_icon_hot, R.string.hot);
        Tab tab_category = new Tab(CategoryFragment.class, R.drawable.selector_icon_category, R.string.category);
        Tab tab_cart = new Tab(CartFragment.class, R.drawable.selector_icon_cart, R.string.cart);
        Tab tab_mine = new Tab(MineFragment.class, R.drawable.selector_icon_mine, R.string.mine);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        mTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }
}
