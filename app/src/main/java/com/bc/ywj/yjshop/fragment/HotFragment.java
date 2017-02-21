package com.bc.ywj.yjshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.HotWaresAdapter;
import com.bc.ywj.yjshop.adapter.decoration.DividerItemDecoration;
import com.bc.ywj.yjshop.entity.PageResult;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.http.OkHttpHelper;
import com.bc.ywj.yjshop.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;

import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HotFragment extends Fragment {
    private static final int STATE_NORMAL=0;//正常状态
    private static final int STATE_REFRESH=1;//刷新
    private static final int STATE_MORE=2;//加载更多
    private int state= STATE_NORMAL;//默认状态是正常状态
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private HotWaresAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private List<Wares> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.hot_rv);
        getData();
        return view;
    }

    private void getData() {
        String url = Contants.API.WARES_HOT + "?curPage=" + currPage + "&pageSize=" + pageSize;
        okHttpHelper.get(url, new SpotsCallBack<PageResult<Wares>>(getContext()) {
            @Override
            public void onSuccess(Response response, PageResult<Wares> waresPageResult) {
                datas = waresPageResult.getList();
                currPage = waresPageResult.getCurrentPage();
                totalPage = waresPageResult.getTotalPage();
                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {
        mAdapter = new HotWaresAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }
}

