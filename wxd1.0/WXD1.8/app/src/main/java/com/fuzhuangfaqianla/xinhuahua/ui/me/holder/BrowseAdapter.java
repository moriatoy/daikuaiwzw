package com.fuzhuangfaqianla.xinhuahua.ui.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.util.DateUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.ViewHolder> {
    private List<List<Map<String,Object>>> datas = new ArrayList<>();
    private int position;

    public BrowseAdapter(int position){
        this.position = position;
    }

    public void addAll(List<List<Map<String,Object>>> datas, boolean isClear){
        if(isClear){
            this.datas.clear();
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public BrowseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_fragment_browse_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrowseAdapter.ViewHolder holder, int position) {
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(holder.imgLogo,String.valueOf(datas.get(position).get(1).get("iconUrl"))));

        holder.tvTitle.setText(String.valueOf(datas.get(position).get(1).get("productName")));

        holder.tvRange.setText(String.valueOf(datas.get(position).get(1).get("loanRange")));

        //去除科学计数法
        NumberFormat nf= NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        long mill = Long.valueOf(String.valueOf(nf.format(datas.get(position).get(0)))).longValue();
//        String time = DateUtil.format(mill,"yyyy-MM-dd");
        String time = DateUtil.getAgoTime(mill);
        if(this.position==0){
            holder.tvTime.setText("申请时间："+time);
        }else if(this.position==1){
            holder.tvTime.setText("浏览时间："+time);
        }

        if(datas.get(position).get(1).get("applicants")==null){
            holder.tvCount.setText("0");
        }else{
            holder.tvCount.setText((int)((double) datas.get(position).get(1).get("applicants"))+"");
        }
    }

    @Override
    public int getItemCount() {
        return datas==null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.fragment_browse_list_item_iv)
        ImageView imgLogo;
        @BindView(R.id.fragment_browse_list_item_tv_title)
        TextView tvTitle;
        @BindView(R.id.fragment_browse_list_item_tv_range)
        TextView tvRange;
        @BindView(R.id.fragment_browse_list_item_tv_time)
        TextView tvTime;
        @BindView(R.id.fragment_browse_list_item_tv_count)
        TextView tvCount;
        @BindView(R.id.fragment_browse_list_item_ll_content)
        LinearLayout llContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            llContent.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()){
                case R.id.fragment_browse_list_item_ll_content:
                    ProductClickHelper.clickBrowse(v.getContext(),position, (double) datas.get(getLayoutPosition()).get(1).get("productId"));
                    break;
            }
        }
    }
}
