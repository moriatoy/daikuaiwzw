package com.fuzhuangfaqianla.xinhuahua.ui.product.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.Intro;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/28.
 */
public class IntroViewBinder extends ItemViewBinder<PinnedHeaderEntity, IntroViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.product_activity_list_item_intro, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PinnedHeaderEntity pinnedHeaderEntity) {
        Intro intro = (Intro) pinnedHeaderEntity.getData();
        holder.tvIntro.setText(intro.getIntro());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_activity_list_item_intro)
        TextView tvIntro;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
