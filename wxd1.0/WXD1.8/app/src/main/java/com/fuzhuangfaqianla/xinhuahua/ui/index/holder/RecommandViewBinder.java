package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.support.annotation.NonNull;
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
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/22.
 */

public class RecommandViewBinder extends ItemViewBinder<OnlineLoan, RecommandViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_recomm_activity_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull OnlineLoan onlineLoan) {
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(holder.imageView,onlineLoan.getIconUrl()));

        holder.tvName.setText(onlineLoan.getProductName());

        holder.tvTitlte.setText(onlineLoan.getLoanRange());

        holder.tvInfo.setText(onlineLoan.getIntro());

        holder.tvDeadLine.setText(onlineLoan.getTimeLimit());

        holder.tvRadio.setText(onlineLoan.getRate());

        holder.tvRateType.setText(onlineLoan.getRateType());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.inflate_recomm_activity_list_item_iv)
        ImageView imageView;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_name)
        TextView tvName;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_title)
        TextView tvTitlte;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_info)
        TextView tvInfo;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_deadline)
        TextView tvDeadLine;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_radio)
        TextView tvRadio;
        @BindView(R.id.inflate_recomm_activity_list_item__ll)
        LinearLayout llContent;
        @BindView(R.id.inflate_recomm_activity_list_item_tv_rateType)
        TextView tvRateType;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            llContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnlineLoan data = (OnlineLoan) getAdapter().getItems().get(getLayoutPosition());

            switch (v.getId()){
                case R.id.inflate_recomm_activity_list_item__ll:
                    ProductClickHelper.clickOnlineProduct(v.getContext(), ZhugeHelper.recomm_from, data);
                    break;
            }
        }
    }
}