package com.bc.ywj.yjshop.adapter;

import android.content.Context;

import com.bc.ywj.yjshop.R;
import com.bc.ywj.yjshop.entity.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {


    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.item_category_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Category item) {
        holder.getTextView(R.id.item_category_tv)
                .setText(item.getName());
    }
}
