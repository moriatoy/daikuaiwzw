package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.Carousel;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;

import java.util.ArrayList;
import java.util.List;


public class RollPagerAdapter extends LoopPagerAdapter {
    private List<Carousel> datas = new ArrayList<>();

    public RollPagerAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    public void addAll(List<Carousel> list, boolean isClear){
        if(isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getRealCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Carousel carousel = datas.get(position);

        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(imageView,carousel.getCarouselPicUrl()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClickHelper.clickCarousel(imageView.getContext(),carousel);
            }
        });

        return imageView;
    }

}
