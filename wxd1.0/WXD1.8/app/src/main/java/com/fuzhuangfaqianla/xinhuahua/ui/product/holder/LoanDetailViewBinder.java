package com.fuzhuangfaqianla.xinhuahua.ui.product.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.LoanDetail;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/28.
 */
public class LoanDetailViewBinder extends ItemViewBinder<PinnedHeaderEntity, LoanDetailViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.product_activity_list_item_loan_detail, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PinnedHeaderEntity pinnedHeaderEntity) {
        LoanDetail detail = (LoanDetail) pinnedHeaderEntity.getData();

        holder.tvUnit.setText(detail.getRateType().substring(0,1));
        holder.tvRateType.setText(detail.getRateType());
        holder.tvRate.setText(detail.getRate());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,TextWatcher {
        @BindView(R.id.product_activity_list_item_loan_detail_day)
        TextView tvDay;
        @BindView(R.id.product_activity_list_item_loan_detail_tv_total)
        TextView tvTotal;
        @BindView(R.id.product_activity_list_item_loan_detail_tv_rate)
        TextView tvRate;
        @BindView(R.id.product_activity_list_item_loan_detail_et_money)
        EditText etMoney;
        @BindView(R.id.product_activity_list_item_loan_detail_et_time)
        EditText etTime;
        @BindView(R.id.product_activity_list_item_loan_detail_tv_unit)
        TextView tvUnit;
        @BindView(R.id.product_activity_list_item_loan_detail_tv_ratetype)
        TextView tvRateType;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            etMoney.setOnClickListener(this);
            etTime.setOnClickListener(this);
            etMoney.addTextChangedListener(this);
            etTime.addTextChangedListener(this);
        }

        @Override
        public void onClick(final View v) {
//            new RecyclerViewScrollUtil(recyclerView).smoothMoveToPosition(2);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvDay.setText("0.00元");
            tvTotal.setText("0.00元");

            String rate_str = tvRate.getText().toString();
            if(TextUtils.isEmpty(rate_str)){
                return;
            }
            double rate = 0;
            try{
                String sub_str = rate_str.substring(0,rate_str.length()-1);
                if(!sub_str.matches("-?[0-9]+.?[0-9]*")){
                    throw new Exception();
                }
                rate = Double.parseDouble(sub_str) * 0.01;
            }catch (Exception e){
                ToastUtil.toastShortShow("利率有误");
            }
            double money;
            double time;
            if(TextUtils.isEmpty(etMoney.getText().toString())){
                money = 0;
            }else {
                money = Double.parseDouble(etMoney.getText().toString());
            }
            if(TextUtils.isEmpty(etTime.getText().toString())){
                time = 0;
            }else {
                time = Double.parseDouble(etTime.getText().toString());
            }

            double total = money * time * rate;

            int unit = 0;
            if(tvUnit.getText().toString().equals("年")){
                unit = 365;
            }else if(tvUnit.getText().toString().equals("月")){
                unit = 30;
            }else if(tvUnit.getText().toString().equals("周")){
                unit = 7;
            }else if(tvUnit.getText().toString().equals("日")){
                unit = 1;
            }

            double total_day = unit * time;
            if(total_day==0){
                return;
            }

            double day_pay = (money+total)/total_day;

            tvDay.setText(String.format("%.2f",day_pay)+"元");
            tvTotal.setText(String.format("%.2f",total)+"元");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
