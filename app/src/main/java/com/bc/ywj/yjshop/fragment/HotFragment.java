package com.bc.ywj.yjshop.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.BaseAdapter;
import com.bc.ywj.yjshop.adapter.BaseViewHolder;
import com.bc.ywj.yjshop.adapter.HWAdapter;
import com.bc.ywj.yjshop.adapter.HotWaresAdapter;
import com.bc.ywj.yjshop.adapter.SimpleAdapter;
import com.bc.ywj.yjshop.adapter.decoration.DividerItemDecoration;
import com.bc.ywj.yjshop.entity.PageResult;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.http.OkHttpHelper;
import com.bc.ywj.yjshop.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HotFragment extends Fragment {
    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新
    private static final int STATE_MORE = 2;//加载更多
    private int state = STATE_NORMAL;//默认状态是正常状态
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private HWAdapter mAdapter;
    @BindView(R.id.hot_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    MaterialRefreshLayout mRefreshLayout;
    private List<Wares> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        initRefreshLayout();
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
        switch (state) {
            case STATE_NORMAL:
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
                break;
            case STATE_REFRESH:
                mAdapter.clearData();
                mAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getItemCount(), datas);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);//开始加载
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currPage <= totalPage) {
                    loadMoreData();
                } else {
                    Toast.makeText(getContext(),
                            "已经没有更多数据", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFRESH;
        getData();
    }

    private void loadMoreData() {
        currPage += 1;
        state = STATE_MORE;
        getData();
    }
}

