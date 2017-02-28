package com.bc.ywj.yjshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.entity.Campaign;
import com.bc.ywj.yjshop.entity.HomeCampaign;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private static final int VIEW_TYPE_L = 0;
    private static final int VIEW_TYPE_R = 1;
    private LayoutInflater mInflater;
    private List<HomeCampaign> mDatas;
    private Context mContext;
    private OnCampaignClickListener mListener;

    public void setmListener(OnCampaignClickListener mListener) {
        this.mListener = mListener;
    }

    public HomeCategoryAdapter(List<HomeCampaign> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_R) {
            return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview2, parent, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HomeCampaign homeCampaigns = mDatas.get(position);
        Log.e("TAG", "位置");
        holder.textTitle.setText(homeCampaigns.getTitle());
        Picasso.with(mContext).load(homeCampaigns.getCpOne()
                .getImgUrl()).into(holder.imageViewBig);
        Picasso.with(mContext).load(homeCampaigns.getCpTwo()
                .getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaigns.getCpThree()
                .getImgUrl()).into(holder.imageViewSmallBottom);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYPE_L;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.title_tv);
            imageViewBig = (ImageView) itemView.findViewById(R.id.big_iv);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.small_top_iv);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.small_bottom_iv);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                anim(v);
            }
        }

        private void anim(final View v) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(
                    v, "rotationX", 0.0f, 360.0f
            ).setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    HomeCampaign homeCampaign = mDatas.get(getLayoutPosition());
                    switch (v.getId()) {
                        case R.id.big_iv:
                            mListener.onClick(v, homeCampaign.getCpOne());
                            break;
                        case R.id.small_top_iv:
                            mListener.onClick(v, homeCampaign.getCpTwo());
                            break;
                        case R.id.small_bottom_iv:
                            mListener.onClick(v, homeCampaign.getCpThree());
                            break;
                    }
                }
            });
            animator.start();
        }
    }

    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }
}
