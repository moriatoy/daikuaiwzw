package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.CarouselList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/21.
 */
public class CarouselViewBinder extends ItemViewBinder<CarouselList, CarouselViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.index_fragment_item_carousel, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CarouselList carousel) {
        holder.setData(carousel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index_fragment_item_carousel_rollviewpager)
        RollPagerView rollPagerView;
        RollPagerAdapter rollPagerAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            initRollPagerView();
        }

        private void initRollPagerView() {
            //设置播放时间间隔
            rollPagerView.setPlayDelay(3000);
            //设置透明度
            rollPagerView.setAnimationDurtion(500);
            //设置小圆点
            rollPagerView.setHintView(new ColorPointHintView(rollPagerView.getContext(), Color.WHITE, Color.argb(62, 255, 255, 255)));
            rollPagerAdapter = new RollPagerAdapter(rollPagerView);
            rollPagerView.setAdapter(rollPagerAdapter);
        }


        public void setData(CarouselList carousel) {
            rollPagerAdapter.addAll(carousel.getDatas(),true);
        }
    }
}
