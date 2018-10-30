package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommendList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/21.
 */
public class TodayRecommendViewBinder extends ItemViewBinder<TodayRecommendList, TodayRecommendViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.index_fragment_item_applet, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TodayRecommendList todayRecommendList) {
        holder.setData(todayRecommendList.getDatas());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index_fragment_item_applet_recyclerview)
        RecyclerView recyclerView;
        @BindView(R.id.index_fragment_item_applet_ll_recommend_class)
        LinearLayout llRecommendClass;
        @BindView(R.id.index_fragment_item_applet_ll_recommend_class_seperator)
        View recommendClassSeperator;
        TodayRecommendAdapter adapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            initRecyclerView();
        }

        private void initRecyclerView() {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            //否则会自动滑动
            recyclerView.setFocusable(false);
            adapter = new TodayRecommendAdapter();
            recyclerView.setAdapter(adapter);
        }

        public void setData(List<TodayRecommend> datas) {
            if(datas==null || datas.size()==0){
                llRecommendClass.setVisibility(View.GONE);
                recommendClassSeperator.setVisibility(View.GONE);
            }else {
                llRecommendClass.setVisibility(View.VISIBLE);
                recommendClassSeperator.setVisibility(View.VISIBLE);
            }

            adapter.addAll(datas,true);
        }
    }
}
