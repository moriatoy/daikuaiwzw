package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class TodayRecommendAdapter extends RecyclerView.Adapter<TodayRecommendAdapter.ViewHolder> {
    List<TodayRecommend> datas = new ArrayList<>();

    public void addAll(List<TodayRecommend> datas, boolean isClear){
        if(isClear){
            this.datas.clear();
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_fragment_item_applet_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodayRecommend data = datas.get(position);

        holder.tvMessage.setText(data.getMessage());

        holder.tvInfo.setText(data.getProductIntro());

        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(holder.iv,data.getPicUrl()));

        holder.tvName.setText(data.getLoanProduct().getProductName());

        holder.tvCount.setText(data.getLoanProduct().getApplicants()+"");
    }

    @Override
    public int getItemCount() {
        return (datas == null) ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.index_fragment_item_applet_list_item_iv)
        ImageView iv;
        @BindView(R.id.index_fragment_item_applet_list_item_tv_name)
        TextView tvName;
        @BindView(R.id.index_fragment_item_applet_list_item_tv_message)
        TextView tvMessage;
        @BindView(R.id.index_fragment_item_applet_list_item_tv_info)
        TextView tvInfo;
        @BindView(R.id.index_fragment_item_applet_list_item_tv_count)
        TextView tvCount;
        @BindView(R.id.index_fragment_item_applet_list_item_ll)
        LinearLayout llContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            llContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TodayRecommend data = datas.get(getLayoutPosition());

            ProductClickHelper.clickTodayRecommend(v.getContext(), ZhugeHelper.today_recomm_from,data);
        }
    }
}
