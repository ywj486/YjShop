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
import android.widget.Toast;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.BaseAdapter;
import com.bc.ywj.yjshop.adapter.HWAdapter;
import com.bc.ywj.yjshop.adapter.decoration.DividerItemDecoration;
import com.bc.ywj.yjshop.entity.PageResult;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.utils.PageUtils;
import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HotFragment extends Fragment {
    @BindView(R.id.hot_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    MaterialRefreshLayout mRefreshLayout;

    private HWAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);

        PageUtils pageUtils = PageUtils.newBuilder()
                .setUrl(Contants.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(new PageUtils.OnPageListener() {
                    @Override
                    public void load(List datas, int totalPage, int totalCount) {
                        mAdapter = new HWAdapter(getContext(), datas);
                        mAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(), "点击了：" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL_LIST));
                    }

                    @Override
                    public void refresh(List datas, int totalPage, int totalCount) {
                        mAdapter.clearData();
                        mAdapter.addData(datas);
                        mRecyclerView.scrollToPosition(0);
                    }

                    @Override
                    public void loadMore(List datas, int totalPage, int totalCount) {
                        mAdapter.addData(mAdapter.getItemCount(), datas);
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                    }
                })
                .setPageSize(20)
                .setRefreshLayout(mRefreshLayout)
                .build(getContext(), new TypeToken<PageResult<Wares>>() {
                }.getType());
        pageUtils.request();
        return view;
    }

//    public void test(){
//        PageUtils pageUtils=null;
//        pageUtils.putParam("aaa","vvv");
//        pageUtils.request();
//    }
}

