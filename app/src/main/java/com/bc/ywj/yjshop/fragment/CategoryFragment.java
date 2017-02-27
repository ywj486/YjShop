package com.bc.ywj.yjshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.BaseAdapter;
import com.bc.ywj.yjshop.adapter.CategoryAdapter;
import com.bc.ywj.yjshop.adapter.HWAdapter;
import com.bc.ywj.yjshop.adapter.WaresAdapter;
import com.bc.ywj.yjshop.adapter.decoration.DividerGridItemDecoration;
import com.bc.ywj.yjshop.adapter.decoration.DividerItemDecoration;
import com.bc.ywj.yjshop.entity.Banner;
import com.bc.ywj.yjshop.entity.Category;
import com.bc.ywj.yjshop.entity.PageResult;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.http.BaseCallBack;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.http.OkHttpHelper;
import com.bc.ywj.yjshop.http.SpotsCallBack;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class CategoryFragment extends Fragment {
    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新
    private static final int STATE_MORE = 2;//加载更多
    private int state = STATE_NORMAL;//默认状态是正常状态
    @BindView(R.id.category_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.category_wares_rv)
    RecyclerView mWaresRecyclerView;
    @BindView(R.id.category_sl)
    SliderLayout mSliderLayout;
    @BindView(R.id.category_mrl)
    MaterialRefreshLayout mRefreshLayout;

    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private CategoryAdapter mCategoryAdapter;
    private WaresAdapter mWaresAdapter;
    private long category_id = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        requestCategoryData();
        requestBannerData();
        initRefreshLayout();
        return view;
    }

    private void requestCategoryData() {
        okHttpHelper.get(Contants.API.CATEGORY_LIST,
                new SpotsCallBack<List<Category>>(getContext()) {

                    @Override
                    public void onSuccess(Response response, List<Category> categories) {
                        showCategoryData(categories);
                        if (categories != null && categories.size() > 0) {
                        Log.e("TAG", "成了=======================");
                            category_id = categories.get(0).getId();
                            requestWares(category_id);
                        Log.e("TAG", "成了tgh=======================");
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        mCategoryAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Category category = mCategoryAdapter.getItem(position);
                category_id = category.getId();
                currPage = 1;
                state = STATE_NORMAL;
                Log.e("TAG", "category_id=================="+category_id);
                requestWares(category_id);
            }
        });
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    private void requestBannerData() {
        String url = Contants.API.BANNER + "?type=2";
        //带对话框的
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                showBannerData(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showBannerData(List<Banner> mBanners) {
        if (mBanners != null) {
            for (Banner banner : mBanners) {
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView
                        //  .description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//设置默认的指示器
        //sliderShow.setCustomIndicator(mIndicator);//设置自定义指示器
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);//设置转场效果
        mSliderLayout.setDuration(3000);

    }

    private void requestWares(long categoryId) {
        String url = Contants.API.WARES_LIST + "?categoryId="
                + categoryId + "&curPage=" + currPage +
                "&pageSize=" + pageSize;
        Log.e("TAG", "OOOOOOOO==="+url);
        okHttpHelper.get(url, new BaseCallBack<PageResult<Wares>>() {
            @Override
            public void onRequestBefore(Request request) {
                Log.e("TAG", "onRequestBefore");
            }

            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("TAG", "onFailure");
            }

            @Override
            public void onSuccess(Response response, PageResult<Wares> waresPageResult) {
                Log.e("TAG", "onSuccess");
                currPage = waresPageResult.getCurrentPage();
                totalPage = waresPageResult.getTotalPage();
                Log.e("TAG", "list===="+waresPageResult.getList());
                showWaresData(waresPageResult.getList());
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.e("TAG", "onError");
            }

            @Override
            public void onResponse(Response response) {
                Log.e("TAG", "onResponse");
            }
        });
    }

    private void showWaresData(List<Wares> datas) {
        Log.e("TAG", "AAAAAAAAAAAAAA");
        switch (state) {
            case STATE_NORMAL:
                if (mWaresAdapter == null) {
                    mWaresAdapter = new WaresAdapter(getContext(), datas);
                    mWaresAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getContext(), "点击了：" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mWaresRecyclerView.setAdapter(mWaresAdapter);
                    mWaresRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mWaresRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mWaresRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
                } else {
                    mWaresAdapter.clearData();
                    mWaresAdapter.addData(datas);
                }
                break;
            case STATE_REFRESH:
                mWaresAdapter.clearData();
                mWaresAdapter.addData(datas);
                mWaresRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mWaresAdapter.addData(mWaresAdapter.getItemCount(), datas);
                mWaresRecyclerView.scrollToPosition(mWaresAdapter.getItemCount());
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
                            "已经没有更多数据",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFRESH;
        requestWares(category_id);
    }

    private void loadMoreData() {
        currPage += 1;
        state = STATE_MORE;
        requestWares(category_id);
    }
}
