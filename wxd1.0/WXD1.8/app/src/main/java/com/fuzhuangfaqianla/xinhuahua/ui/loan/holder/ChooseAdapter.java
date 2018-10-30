package com.fuzhuangfaqianla.xinhuahua.ui.loan.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.bean.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongchang on 2017/9/23.
 */

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {
    List<Sort> datas = new ArrayList<>();

    public void addAll(List<Sort> datas, boolean isClear){
        if(isClear){
            this.datas.clear();
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_choose_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sort data = datas.get(position);

        holder.tv.setTextColor(holder.tv.getContext().getResources().getColor(R.color.color_33));
        if(data.isSelectd()){
            holder.tv.setTextColor(holder.tv.getContext().getResources().getColor(R.color.colorMain));
        }else {
            holder.tv.setTextColor(holder.tv.getContext().getResources().getColor(R.color.color_33));
        }

        holder.tv.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return datas==null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.inflate_choose_item_tv)
        TextView tv;
        @BindView(R.id.inflate_choose_item_ll_content)
        LinearLayout llContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Sort sort = datas.get(getLayoutPosition());
            if(chooseItemClickListener!=null){
                //zhuge click
                Map<String,String> map = new HashMap<>();
                map.put("pagename","贷款大全");
                switch (sort.getType()){
                    case "ocp":
                        map.put("from","身份");
                        break;

                    case "loantype":
                        map.put("from","贷款类型");
                        break;

                    case "money":
                        map.put("from","金额");
                        break;
                }
                map.put("name",sort.getName());
                ZhugeHelper.clickSearch(v.getContext(),map);

                chooseItemClickListener.onItemClick(v,sort,getLayoutPosition());
            }
        }
    }


    private ChooseItemClickListener chooseItemClickListener;

    public void setChooseItemClickListener(ChooseItemClickListener chooseItemClickListener) {
        this.chooseItemClickListener = chooseItemClickListener;
    }

    public interface ChooseItemClickListener{
        void onItemClick(View view,Sort sort,int position);
    }

    /**
     * 标记颜色
     */
    public void showSelectedColor(List<Sort> datas, int position){
        //先替换数据
        this.datas.clear();
        this.datas.addAll(datas);
        //先全部清空
        for(Sort sort : this.datas){
            sort.setSelectd(false);
        }
        this.datas.get(position).setSelectd(true);
        notifyDataSetChanged();
    }

}
