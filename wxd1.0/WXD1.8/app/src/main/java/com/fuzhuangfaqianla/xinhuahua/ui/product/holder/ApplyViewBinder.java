package com.fuzhuangfaqianla.xinhuahua.ui.product.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.Apply;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/28.
 */
public class ApplyViewBinder extends ItemViewBinder<PinnedHeaderEntity, ApplyViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.product_activity_list_item_apply, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PinnedHeaderEntity pinnedHeaderEntity) {
        Apply apply = (Apply) pinnedHeaderEntity.getData();
        holder.setData(apply.getDatas());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_activity_list_item_apply_recyclerview)
        RecyclerView recyclerView;
        ApplyAdapter adapter;

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
            //自动滑动  应该设置控件的focusable为false  或者设置它的父控件为true
//            recyclerView.setFocusable(false);
//            recyclerView.setFocusableInTouchMode(false);
            recyclerView.setNestedScrollingEnabled(false);
            adapter = new ApplyAdapter();
            recyclerView.setAdapter(adapter);
        }

        public void setData(List<Object[]> datas) {
            adapter.addAll(datas,true);
        }
    }
}
