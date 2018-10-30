package com.fuzhuangfaqianla.xinhuahua.ui.product.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.willy.ratingbar.BaseRatingBar;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.ProductInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/10/18.
 */
public class ProductInfoViewBinder extends ItemViewBinder<PinnedHeaderEntity, ProductInfoViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.product_activity_list_item_product_info, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PinnedHeaderEntity pinnedHeaderEntity) {
        ProductInfo data = (ProductInfo) pinnedHeaderEntity.getData();

        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(holder.ivIcon,data.getIconUrl()));
        holder.tvRange.setText(data.getLoanRange());
        holder.tvPaymentMethod.setText("还款方式："+data.getPaymentMethod());
        holder.ratingBar.setRating(data.getRcmIndex());
        holder.tvRateType.setText(data.getRateType());
        holder.tvRate.setText(data.getRate());
        holder.tvDeadline.setText(data.getTimeLimit());
        holder.tvApplicants.setText(data.getApplicants()+"");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_activity_list_item_product_info_iv_icon)
        ImageView ivIcon;
        @BindView(R.id.product_activity_list_item_product_info_tv_range)
        TextView tvRange;
        @BindView(R.id.product_activity_list_item_product_info_tv_payment_method)
        TextView tvPaymentMethod;
        @BindView(R.id.product_activity_list_item_product_info_star)
        BaseRatingBar ratingBar;
        @BindView(R.id.product_activity_list_item_product_info_tv_rateType)
        TextView tvRateType;
        @BindView(R.id.product_activity_list_item_product_info_tv_rate)
        TextView tvRate;
        @BindView(R.id.product_activity_list_item_product_info_tv_deadline)
        TextView tvDeadline;
        @BindView(R.id.product_activity_list_item_product_info_tv_applicants)
        TextView tvApplicants;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
