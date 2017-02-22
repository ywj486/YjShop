package com.bc.ywj.yjshop.adapter;

import android.content.Context;
import android.net.Uri;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.entity.Wares;
import com.bc.ywj.yjshop.http.Contants;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class HWAdapter extends SimpleAdapter<Wares> {
    public HWAdapter(Context context,  List<Wares> datas) {
        super(context, R.layout.item_hot_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Wares item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.hot_wares_img_sdv);
//        draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL + item.getImgUrl()));
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
        holder.getTextView(R.id.hot_wares_title_tv).setText(item.getName());
        holder.getTextView(R.id.hot_wares_price_tv).setText(item.getPrice() + "");
        //条目中的按钮点击
        //holder.getButton(R.id.xxx).setOnClickListener();
    }
}
