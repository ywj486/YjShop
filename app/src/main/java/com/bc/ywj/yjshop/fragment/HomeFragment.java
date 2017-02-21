package com.bc.ywj.yjshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.adapter.HomeCategoryAdapter;
import com.bc.ywj.yjshop.adapter.decoration.CardViewItemDecoration;
import com.bc.ywj.yjshop.entity.Banner;
import com.bc.ywj.yjshop.entity.Campaign;
import com.bc.ywj.yjshop.entity.HomeCampaign;
import com.bc.ywj.yjshop.http.BaseCallBack;
import com.bc.ywj.yjshop.http.Contants;
import com.bc.ywj.yjshop.http.OkHttpHelper;
import com.bc.ywj.yjshop.http.SpotsCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HomeFragment extends Fragment {
    private SliderLayout sliderShow;
    PagerIndicator mIndicator;
    RecyclerView mRecyclerView;
    private HomeCategoryAdapter mAdapter;
    private Gson mGson = new Gson();
    private List<Banner> mBanners;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderShow = (SliderLayout) view.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        requestImages();
        initRecyclerView(view);
        return view;
    }

    private void requestImages() {
        String url = Contants.API.BANNER + "?type=1";
        //带对话框的
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
// 不带对话框       okHttpHelper.get(url, new BaseCallBack<List<Banner>>() {
//            @Override
//            public void onRequestBefore(Request request) {
//
//            }
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onSuccess(Response response, List<Banner> banners) {
//                Log.e("TAG", "banner---->"+banners.size());
//                mBanners=banners;
//                initSlider();
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//            }
//        });
//  String url = "http://101.200.167.75:8080/phoenixshop/banner/query";
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("type", "1")
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
////        Request request = new Request.Builder()
////                .url(url)
////                .build();
//        client.newCall(request).enqueue(new Callback() {
//            //请求网络时穿不可恢复的错误时调用该方法
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            //请求网络成功时调用该方法
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //判断http状态码
//                if (response.isSuccessful()) {
//                    String json = response.body().string();
//                    Log.e("TAG", "json=" + json);
//                    Type type = new TypeToken<List<Banner>>() {
//                    }.getType();
//                    mBanners = mGson.fromJson(json, type);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            initSlider();
//                        }
//                    });
//                }
//            }
//        });
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        okHttpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallBack<List<HomeCampaign>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initData(homeCampaigns);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }
        });
//        List<HomeCampaign> datas = new ArrayList<>();
//        HomeCampaign category = new HomeCampaign("热门活动", R.drawable.img_big_1,
//                R.drawable.img_1_small1, R.drawable.img_1_small2);
//        datas.add(category);
//        category = new HomeCampaign("品牌街", R.drawable.img_big_2,
//                R.drawable.img_2_small1, R.drawable.img_2_small2);
//        datas.add(category);
//        category = new HomeCampaign("金融街 包赚翻", R.drawable.img_big_3,
//                R.drawable.img_3_small1, R.drawable.img_3_small2);
//        datas.add(category);
//        category = new HomeCampaign("超值购", R.drawable.img_big_0,
//                R.drawable.img_0_small1, R.drawable.img_0_small2);
//        datas.add(category);
//        mAdapter = new HomeCategoryAdapter(datas);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void initData(List<HomeCampaign> homeCampaigns) {
        mAdapter = new HomeCategoryAdapter(homeCampaigns, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new CardViewItemDecoration());

        mAdapter.setmListener(new HomeCategoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getContext(), "title=" + campaign.getTitle(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initSlider() {
        if (mBanners != null) {
            for (Banner banner : mBanners) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView
                        .description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                sliderShow.addSlider(textSliderView);
            }
        }
        //sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//设置默认的指示器
        sliderShow.setCustomIndicator(mIndicator);//设置自定义指示器
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setPresetTransformer(SliderLayout.Transformer.RotateUp);//设置转场效果
        sliderShow.setDuration(3000);

//        sliderShow.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int p   ositionOffsetPixels) {
//                Log.e("TAG","onPageScrolled------->1");//在滚动中调用
//            }
//
//            @Override\y
//            public void onPageSelected(int position) {
//                Log.e("TAG","onPageSelected------->2");//新页卡被选中的时候调用
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Log.e("TAG","onPageScrollStateChanged------->3");//在滚动状态改变时调用
//
//            }
//        });
    }

    @Override
    public void onStart() {
        sliderShow.startAutoCycle();
        super.onStart();
    }

    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            sliderShow.stopAutoCycle();
        } else {
            sliderShow.startAutoCycle();
        }
        super.onHiddenChanged(hidden);
    }
}
